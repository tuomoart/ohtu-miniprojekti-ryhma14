package library.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author emma
 */
public interface WebLinkDao {

    boolean addLinkToDatabase(String URL, String comment, Boolean read) throws SQLException;

    List<List<String>> getLinks() throws SQLException;

    boolean clearDatabase();

    boolean remove(int id);

    boolean toggleRead(int id);

}
