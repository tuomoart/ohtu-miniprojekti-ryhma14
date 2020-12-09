/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.sql.*;
import java.util.*;

/**
 *
 * @author hiski
 */
public class SQLPodcastDao implements PodcastDao {

    final private Database database;

    public SQLPodcastDao(String address) throws SQLException {
        this.database = new Database(address);
    }

    @Override
    public boolean addPodcastToDatabase(String title, String series, String creator,
            String url, boolean read) throws SQLException {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Podcasts(title, series, creator, url, read) "
                    + "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, series);
            ps.setString(3, creator);
            ps.setString(4, url);
            ps.setInt(5, read ? 1 : 0);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<List<String>> getPodcasts() throws SQLException {
        List<List<String>> podcasts = new ArrayList<>();
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Podcasts");
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                List<String> podcast = new ArrayList<>();

                
                podcast.add(results.getString("title"));
                podcast.add(results.getString("series"));
                podcast.add(results.getString("creator"));
                podcast.add(results.getString("url"));
                podcast.add(results.getString("read"));
                
                podcasts.add(podcast);
            }
            connection.close();
            return podcasts;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return podcasts;
        }
    }

    @Override
    public boolean clearDatabase() {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Books");
            ps.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
