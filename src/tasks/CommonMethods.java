package tasks;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonMethods {

    protected String getNameOfColumnWithId(String table) {
        if(table.equals("people")) return "person_id";
        if(table.equals("week")) return "day_id";
        if(table.equals("tasks")) return "task_id";
        return null;
    }

    protected String getNameOfColumnWithName(String table) {
        if(table.equals("people")) return "name";
        if(table.equals("week")) return "day";
        if(table.equals("tasks")) return "task_name";
        return null;
    }

    protected void addRowsToTable(DefaultTableModel tableModel, ResultSet rs)
            throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        String[] result = new String[columnCount];
        while(rs.next()) {
            for(int i=0; i<columnCount; i++) {
                result[i] = rs.getString((i+1));
            }
            tableModel.addRow(result);
        }
    }
}
