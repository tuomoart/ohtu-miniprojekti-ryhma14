package library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emma
 */
public class SQLWebLinkDao implements WebLinkDao {

    final private Database database;

    public SQLWebLinkDao(String address) throws SQLException {
        this.database = new Database(address);
    }

    @Override
    public boolean addLinkToDatabase(String URL, String comment, Boolean read) throws SQLException {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO Links(url, comment, read) "
                                      + "VALUES (?, ?, ?)");
            ps.setString(1, URL);
            ps.setString(2, comment);

            int r = 0;
            if (read) {
                r = 1;
            }

            ps.setInt(3, r);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<List<String>> getLinks() throws SQLException {
        List<List<String>> links = new ArrayList();

        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM Links");
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                List<String> link = new ArrayList();

                link.add(results.getString("url"));
                link.add(results.getString("comment"));
                link.add(results.getString("id"));
                link.add(results.getString("read"));

                links.add(link);
            }

            connection.close();
            return links;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return links;
        }
    }

    @Override
    public boolean clearDatabase() {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM Links");
            ps.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection
                    .prepareStatement("DELETE FROM Links WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean toggleRead(int id) {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE Links "
                            + "SET read = ((read | 1) - (read & 1))"
                            + "WHERE id = ?");
            ps.setInt(1, id);
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
}
