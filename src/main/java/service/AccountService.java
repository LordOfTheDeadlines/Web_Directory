package service;

import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static Map<String, UsersDataSet> sessionBase = new HashMap<>();
    private DBService base = new DBService();

    public AccountService(){
    }

    public boolean AddNewUser(String login, String password, String email) {
        UsersDataSet user = new UsersDataSet(login, password, email);
        if (base.getUser(login) != null)
            return false;
        try {
            base.addUser(user.getLogin(), user.getPass(), user.getEmail());
        }
        catch(HibernateException e) {return false;}
        return true;
    }

    public boolean FindUser(String login){
        return base.getUser(login) == null;
    }

    public boolean AuthorizateUser(UsersDataSet authProfile, String sessionID){
        UsersDataSet baseProfile = base.getUser(authProfile.getLogin());
        if(baseProfile != null)
            if(baseProfile.getPass().compareTo(authProfile.getPass()) == 0){
                sessionBase.put(sessionID, baseProfile);
                return true;
            }
        return false;
    }

    public String getLoginBySessionId(String sessionID){
        if(sessionBase.containsKey(sessionID)){
            return sessionBase.get(sessionID).getLogin();
        }
        return null;
    }
    public boolean CheckSessionId(String sessionID){
        return sessionBase.containsKey(sessionID);
    }

    public boolean Quit(String sessionID){
        if(sessionBase.containsKey(sessionID)){
            sessionBase.remove(sessionID);
            return true;
        }
        return false;
    }
}
