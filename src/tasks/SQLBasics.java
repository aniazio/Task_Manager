package tasks;

import java.sql.*;

public class SQLBasics {

    protected static String schema = "tasks";
    private static String user = "root";
    private static String password = "Flp2,5-11";

    protected static Connection startConnection()
            throws SQLException {
        return DriverManager.getConnection(("jdbc:mysql://localhost:3306/" + schema), user, password);
    }

    protected static void closeSQLObjects(ResultSet rs, Statement stm, PreparedStatement pstm, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException exc) {
                System.out.println("Exeption during closing Result Set.");
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException exc) {
                System.out.println("Exeption during closing Statement.");
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException exc) {
                System.out.println("Exeption during closing Prepared Statement.");
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException exc) {
                System.out.println("Exeption during closing connection with database.");
            }
        }
    }
}
