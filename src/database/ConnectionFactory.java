package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PASSWORD = "@ab1#cd2";
    private static final String DATABASE_NAME = "gerenciamentocontatos";
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE_NAME;

    public static Connection createConnectionToMySQL() throws Exception{
        Class.forName(DRIVER);

        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

        return connection;
    }
    
}
