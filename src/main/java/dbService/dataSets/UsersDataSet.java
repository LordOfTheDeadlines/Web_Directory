package dbService.dataSets;

import javax.persistence.*;

@Entity
@Table(name= "users")
public class UsersDataSet {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "pass")
    private String pass;
    @Column(name = "email")
    private String email;

    public UsersDataSet(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public String getLogin() {return login;}
    public String getPass() {return pass;}
    public String getEmail() {return email;}
}

