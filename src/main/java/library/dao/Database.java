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
    
    private String databaseAddress = "jdbc:sqlite:database.db";
    
    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseAddress);
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Books(title VARCHAR, author VARCHAR, year VARCHAR, pages VARCHAR, isbn VARCHAR);");
        connection.close();
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
}
