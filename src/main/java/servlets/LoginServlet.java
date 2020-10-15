package servlets;

import model.UserProfile;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/authorization")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        UserProfile user = AccountService.getUserByLogin(login);

        if (user == null){
            req.getRequestDispatcher("/reg.html").forward(req, resp);
            return;
        }

        if (!pass.equals(user.getPass())){
            req.getRequestDispatcher("/log.html").forward(req, resp);
            return;
        }

        AccountService.addSession(req.getSession().getId(),user);
        String path = "http://localhost:8888/?path=C:\\Users\\"+login;
        resp.sendRedirect(path);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AccountService.deleteSession(req.getSession().getId());
        req.getRequestDispatcher("/log.html").forward(req, resp);
    }
}