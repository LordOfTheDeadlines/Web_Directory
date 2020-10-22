package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;
    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }
    public UsersDataSet get(String login) {
        try {
            return executor.execQuery("select * from users where login='" + login + "'", result -> {
                result.next();
                return new UsersDataSet(result.getString(1), result.getString(2), result.getString(3));
            });
        }
        catch(SQLException e){
            return null;
        }
    }
    public void insertUser(String login, String passsword, String email) throws SQLException {
        executor.execUpdate("insert into users (login, passsword, email) values ('" + login + "','"+ passsword + "','"+ email + "')");
    }
    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (login varchar(256), passsword varchar(256), email varchar(256), primary key (login))");
    }
    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }

}