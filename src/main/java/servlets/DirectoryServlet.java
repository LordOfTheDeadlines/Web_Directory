package servlets;

import model.MyFile;
import model.UserProfile;
import service.AccountService;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryServlet extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        resp.setContentType("text/html");
        String sessionId = req.getSession().getId();
        UserProfile profile = AccountService.getUserBySessionId(sessionId);
        String path = req.getParameter("path");
        if (profile == null){
            req.getRequestDispatcher("/log.html").forward(req, resp);
            return;
        }
        if (path.contains("C:\\Users\\"+profile.getLogin())){
            MyFile file = new MyFile(Paths.get(path));
            if (file.isDirectory()) {
                req.setAttribute("dateTimeNow", new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date()));
                req.setAttribute("files", file.getListFiles(Paths.get(path)));
                req.setAttribute("pathFile", Paths.get(path));
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
            if (file.isFile()) {
                resp.setHeader("Content-Type", "application/octet-stream");
                resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
                resp.setContentType("application/octet-stream");
                resp.setHeader("Content-Disposition","attachment;fileName=\""+file.getName()+"\"");
                FileInputStream fileIn = new FileInputStream(file);
                ServletOutputStream out = resp.getOutputStream();
                byte[] outputByte = new byte[4096];
                while(fileIn.read(outputByte, 0, 4096) != -1){
                    out.write(outputByte, 0, 4096);
                }
                fileIn.close();
                out.flush();
                out.close();
            }
        }
        else{
            req.setAttribute("path", "C:\\Users\\"+profile.getLogin());
            req.getRequestDispatcher("/warning.html").forward(req, resp);
        }
    }
}
