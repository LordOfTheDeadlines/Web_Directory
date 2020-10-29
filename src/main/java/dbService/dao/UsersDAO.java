package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

//    private Executor executor;
//    public UsersDAO(Connection connection) {
//        this.executor = new Executor(connection);
//    }
//    public UsersDataSet getUser(String login) {
//        try {
//            //передавать логин в качестве параметра (не statement, а preparestatement)
//            return executor.execQuery("select * from users where login='" + login + "'", result -> {
//                result.next();
//                return new UsersDataSet(result.getString(1), result.getString(2), result.getString(3));
//            });
//        }
//        catch(SQLException e){
//            return null;
//        }
//    }
//    public void insertUser(String login, String pass, String email) throws SQLException {
//        executor.execUpdate("insert into users (login, passsword, email) values ('" + login + "','"+ pass + "','"+ email + "')");
//    }
//    public void createTable() throws SQLException {
//        executor.execUpdate("create table if not exists users (login varchar(256), passsword varchar(256), email varchar(256), primary key (login))");
//    }
//    public void dropTable() throws SQLException {
//        executor.execUpdate("drop table users");
//    }
private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(String login) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, login);
    }

    public String getUserLogin(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getLogin();
    }

    public String insertUser(String login, String pass, String email) throws HibernateException {
        return (String) session.save(new UsersDataSet(login, pass, email));
    }
}