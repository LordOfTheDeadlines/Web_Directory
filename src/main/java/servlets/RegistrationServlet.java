package servlets;

import model.UserProfile;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet  {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String email = req.getParameter("email");
        if (login==null|| pass==null || email==null){
            req.getRequestDispatcher("/registration").forward(req, resp);
            return;
        }
        AccountService.addNewUser(new UserProfile(login, pass, email));

        File file = new File("C:\\Users\\"+login);
        String path = "http://localhost:8888/?path=C:\\Users\\"+login+"/";
        resp.sendRedirect(new String(path.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    }
}