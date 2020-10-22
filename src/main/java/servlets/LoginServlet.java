package servlets;

import dbService.dataSets.UsersDataSet;
import service.AccountService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorization")
public class LoginServlet extends HttpServlet {

AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String email = req.getParameter("email");
        UsersDataSet authProfile = new UsersDataSet(login, pass, email);

        if(accountService.AuthorizateUser(authProfile, req.getSession().getId())){
            String path = "http://localhost:8888/?path=C:\\Users\\"+login;
            resp.sendRedirect(path);
        }
        else{
            req.getRequestDispatcher("/reg.html").forward(req, resp);
        }
    }
}