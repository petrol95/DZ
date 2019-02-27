package mvcrestful;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class VisitDAO implements AutoCloseable {

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

    // Method for POST-request
    public Map insertVisit(int persId, int pageId) {
        LocalDate date = LocalDate.now();
        Map<String, Integer> resMap = new HashMap<String, Integer>();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Visits (PERS_ID, PAGE_ID, " +
                    "VISIT_DATE) VALUES (" + persId + ", " + pageId + ", '" + date + "')");
            if (statement.executeUpdate() > 0) {
                resMap.put("visits", getAllVisits(date, date));
                resMap.put("visitors", getUniqueVisitors(date, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resMap;
    }

    // Method for GET-request
    public Map getStat(LocalDate dateFrom, LocalDate dateTo) {
        Map<String, Integer> resMap = new HashMap<String, Integer>();
        resMap.put("visits", getAllVisits(dateFrom, dateTo));
        resMap.put("visitors", getUniqueVisitors(dateFrom, dateTo));
        resMap.put("frequenters", getFreqVisitors(dateFrom, dateTo));
        return resMap;
    }

    // All visits
    private int getAllVisits(LocalDate dateFrom, LocalDate dateTo) {
        int col = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(ID) FROM Visits WHERE VISIT_DATE BETWEEN '" +
                    dateFrom + "' AND '" + dateTo + "'");
            while (resultSet.next()) {
                col = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        return col;
    }

    // Unique visitors
    private int getUniqueVisitors(LocalDate dateFrom, LocalDate dateTo) {
        int col = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(DISTINCT PERS_ID) FROM Visits WHERE " +
                            "VISIT_DATE BETWEEN '" + dateFrom + "' AND '" + dateTo + "'");
            while (resultSet.next()) {
                col = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        return col;
    }

    // Const visitors
    private int getFreqVisitors(LocalDate dateFrom, LocalDate dateTo) {
        int col = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(PERS_ID) FROM Visits WHERE " +
                    "VISIT_DATE BETWEEN '" + dateFrom + "' AND '" + dateTo + "' GROUP BY PERS_ID HAVING " +
                    "COUNT(DISTINCT PAGE_ID) > 9");
            while (resultSet.next()) {
                col = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query", e);
        }
        return col;
    }

}