package servlets;

import service.AccountService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet  {

    AccountService accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String email = req.getParameter("email");
        if (login==null|| pass==null || email==null){
            req.getRequestDispatcher("/registration").forward(req, resp);
            return;
        }
        System.out.println(login+pass+email);
        if(accountService.AddNewUser(login, pass, email)){
            File file = new File("C:\\Users\\"+login);
            file.mkdirs();
            String path = "/?path=C:\\Users\\"+login+"/";
            resp.sendRedirect(path);
        }
        else {
            resp.getWriter().println("User with such login has already exist");
        }
    }
}