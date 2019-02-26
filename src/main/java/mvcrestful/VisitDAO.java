package mvcrestful;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private Map<String, Integer> result;
    private final Connection connection;

    public VisitDAO() {
        connectDriver();
        this.connection = createConnection("visits.db");
        this.result = new HashMap<String, Integer>();
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

    public Map insertVisit(int persId, int pageId) {

        Date date = new Date(); // формат даты!!!
        Map<String, Integer> resMap = new HashMap<String, Integer>();
        resMap = null;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Visits (PERS_ID, PAGE_ID, " +
                    "VISIT_DATE) VALUES (" + persId + ", " + pageId + ", '" + date + "')");
            if (statement.executeUpdate() > 0) {
                System.out.println(getAllVisits(date, date));
                resMap.put("visits", getAllVisits(date, date));
            //    resMap.put("visitors", getUniqueVisitors(date, date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // All visits
    public int getAllVisits(Date dateFrom, Date dateTo) {
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
    public int getUniqueVisitors(Date dateFrom, Date dateTo) {
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
    public int getConstVisitors(Date dateFrom, Date dateTo) {
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