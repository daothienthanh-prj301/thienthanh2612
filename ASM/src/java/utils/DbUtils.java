package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    
    private static final String DB_NAME = "CyberNet";
    private static final String DB_USER_NAME = "sa";       
    private static final String DB_PASSWORD = "12345";    

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        return DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
    }

    
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("✅ Kết nối thành công!");
                conn.close();
            }
        } catch (Exception ex) {
            System.err.println("❌ Kết nối thất bại: " + ex.getMessage());
        }
    }
}
