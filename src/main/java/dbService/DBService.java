package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
private final Connection connection;
    public DBService() {
        this.connection = getH2Connection();
    }
    public UsersDataSet getUser(String login) {
        return (new UsersDAO(connection).get(login));
    }
    public void addUser(String login, String passsword, String email) throws SQLException {
        connection.setAutoCommit(false);
        UsersDAO dao = new UsersDAO(connection);
        dao.createTable();
        dao.insertUser(login, passsword, email);
        connection.commit();
        //return dao.getUserId(name);
    }
    public void cleanUp() throws SQLException {
        UsersDAO dao = new UsersDAO(connection);
        dao.dropTable();
    }
    public void printConnectInfo() throws SQLException {
        System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
        System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
        System.out.println("Driver: " + connection.getMetaData().getDriverName());
        System.out.println("Autocommit: " + connection.getAutoCommit());
    }
    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "tully";
            String pass = "tully";
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);
            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}