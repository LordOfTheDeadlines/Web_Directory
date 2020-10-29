package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

//public class DBService {
//private final Connection connection;
//    public DBService() {
////        this.connection = getH2Connection();
//        this.connection = getMysqlConnection();
//    }
//    public UsersDataSet getUser(String login) {
//        return (new UsersDAO(connection).getUser(login));
//    }
//    public void addUser(String login, String pass, String email) throws SQLException {
//        connection.setAutoCommit(false);
//        UsersDAO dao = new UsersDAO(connection);
//        dao.createTable();
//        dao.insertUser(login, pass, email);
//        connection.commit();
//    }
//
//    public void printConnectInfo() throws SQLException {
//        System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
//        System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
//        System.out.println("Driver: " + connection.getMetaData().getDriverName());
//        System.out.println("Autocommit: " + connection.getAutoCommit());
//    }
//    public static Connection getH2Connection() {
//        try {
//            String url = "jdbc:h2:./h2db";
//            String name = "tully";
//            String pass = "tully";
//            JdbcDataSource ds = new JdbcDataSource();
//            ds.setURL(url);
//            ds.setUser(name);
//            ds.setPassword(pass);
//            return DriverManager.getConnection(url, name, pass);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static Connection getMysqlConnection() {
//        try {
//            StringBuilder url = new StringBuilder();
//
//            url.
//                append("jdbc:mysql://").        //db type
//                append("localhost:").           //host name
//                append("3306/").                //port
//                append("db_example2?").          //db name
//                append("user=onlus&").          //login
//                append("password=28udarov&").   //password
//                append("useSSL=false&").
//                append("serverTimezone=UTC");
//
//            System.out.println("URL: " + url + "\n");
//            return DriverManager.getConnection(url.toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;

    public DBService() {
//        Configuration configuration = getH2Configuration();
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example2?serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "onlus");
        configuration.setProperty("hibernate.connection.password", "28udarov");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public UsersDataSet getUser(String login) {
        try {
            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet dataSet = dao.get(login);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public void addUser(String login, String passsword, String email) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.insertUser(login, passsword, email);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}