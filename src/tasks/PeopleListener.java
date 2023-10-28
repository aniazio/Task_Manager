package tasks;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PeopleListener {

    private Connection conn = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private CommonMethods cm = new CommonMethods();

    protected void selectAndPrintInSwing() {
        try {
            DefaultTableModel tableModel = (DefaultTableModel) PeopleSwing.ps.tableSwing.getModel();
            tableModel.setRowCount(0);
            startConnection();
            stm = conn.createStatement();
            String str = ("select name from people;");
            rs = stm.executeQuery(str);
            cm.addRowsToTable(tableModel, rs);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    protected void insertIntoPeople(String value) {
        try {
            startConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery("select max(person_id) from people");
            rs.next();
            int id = rs.getInt("max(person_id)");
            pstm = conn.prepareStatement("insert into people values (?, ?)");
            pstm.setInt(1, (id+1));
            pstm.setString(2, value);
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    protected void updatePeople(String oldName, String newName) {
        try {
            startConnection();
            pstm = conn.prepareStatement("select person_id from people where name = ?");
            pstm.setString(1,oldName);
            rs = pstm.executeQuery();
            rs.next();
            int id = rs.getInt("person_id");
            pstm = conn.prepareStatement("update people set name = ? where person_id = ?");
            pstm.setString(1, newName);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    protected void deleteFromPeople(String name) {
        try {
            startConnection();
            pstm = conn.prepareStatement("delete from people where name = ?");
            pstm.setString(1, name);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    private void startConnection()
            throws SQLException {
        conn = SQLBasics.startConnection();
    }

    private void closeSQLObjects() {
        SQLBasics.closeSQLObjects(rs, stm, pstm, conn);
    }
}
