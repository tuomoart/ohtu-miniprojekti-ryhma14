/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;
import java.sql.*;

/**
 *
 * @author hiira
 */
public class Database {
    
    private String databaseAddress;
    
    public Database(String address) throws SQLException {
        this.databaseAddress = address;
        Connection connection = DriverManager.getConnection(databaseAddress);
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Books(id INTEGER PRIMARY KEY, title VARCHAR, author VARCHAR, year VARCHAR, pages VARCHAR, isbn VARCHAR, read INTEGER);");
        statement.execute("CREATE TABLE IF NOT EXISTS Podcasts(id INTEGER PRIMARY KEY, title VARCHAR, series VARCHAR, creator VARCHAR, url VARCHAR, read INTEGER);");
        connection.close();
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
}
