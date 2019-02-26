package mvcrestful;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.Date;

@Repository
public class VisitDAO implements AutoCloseable {

    private static final Map<String, Visit> visitMap = new HashMap<String, Visit>();
    private final Connection connection;

    public VisitDAO() {
        connectDriver();
        this.connection = createConnection("visits.db");
    }

    private Connection createConnection(String dbUrl) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Invalid database URL: " + dbUrl);
        }
    }

    private void connectDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find JDBC driver", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public boolean insertVisit(int persId, int pageId) {
        Date date = new Date();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Visits (PERS_ID, PAGE_ID, " +
                    "VISIT_DATE) VALUES ( " + persId + ", " + pageId + ", '" + date + "')");
            if (statement.executeUpdate() > 0)
               return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getAllVisits() {
        int col = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Visits");
            while (resultSet.next()) {
                col = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        return col;
    }


//    public Visit addVisit(String userID, String pageID) {
//        Visit visit = new Visit("E07", userID, pageID);
//        visitMap.put(visit.getId(), visit);
//        return visit;
//    }

//    public List<Visit> getAllVisits() {
//        Collection<Visit> c = visitMap.values();
//        List<Visit> list = new ArrayList<Visit>();
//        list.addAll(c);
//        return list;
//    }

}
