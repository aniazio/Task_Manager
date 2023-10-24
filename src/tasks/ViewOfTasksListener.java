package tasks;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ViewOfTasksListener {

    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private CommonMethods cm = new CommonMethods();

    protected void showListOfPeopleOrDays(String table) {
            DefaultTableModel tableModel = getTableModel(table);
            tableModel.setRowCount(0);

            String colName = cm.getNameOfColumnWithName(table);
            String colId = cm.getNameOfColumnWithId(table);

            if(table.equals("week")) {
                String[] row = {"(all week)"};
                tableModel.addRow(row);
            }

        try {
            startConnection();
            String str = ("select " + colName + " from " + table + " order by " + colId + " ;");
            pstm = conn.prepareStatement(str);
            rs = pstm.executeQuery();
            cm.addRowsToTable(tableModel, rs);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    private DefaultTableModel getTableModel(String table) {
        if(table.equals("people")) return (DefaultTableModel) TasksMainApp.vt.listOfPeople.getModel();
        if(table.equals("week")) return (DefaultTableModel) TasksMainApp.vt.listOfDays.getModel();
        if(table.equals("tasks")) return (DefaultTableModel) TasksMainApp.vt.listOfTasks.getModel();
        return null;
    }

    protected void showTasks(ActionEvent actionEvent) {
        int dayRow = TasksMainApp.vt.listOfDays.getSelectedRow();
        if(dayRow == -1) return;

        try {
            startConnection();
            if(dayRow == 0) showTasksWithNoDay();
            else showTaskWithDaySpecified(dayRow);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    private void showTaskWithDaySpecified(int dayRow)
            throws SQLException {
        DefaultTableModel tableModel = getTableModel("tasks");
        tableModel.setRowCount(0);

        String dayName = TasksMainApp.vt.listOfDays.getValueAt(dayRow,0).toString();
        int dayId = getIdFromName(dayName,"week");

        int personRow = TasksMainApp.vt.listOfPeople.getSelectedRow();
        if(personRow == -1) return;
        String personName = TasksMainApp.vt.listOfPeople.getValueAt(personRow,0).toString();
        int personId = getIdFromName(personName, "people");

        String str = "select task_name, (?) , done " +
                "from tasks join tasks_and_week on tasks.task_id = tasks_and_week.task_id " +
                "where tasks.task_id in (select task_id from tasks_and_people where person_id = (?)) " +
                "and tasks_and_week.day_id = (?) " +
                "order by tasks.task_id;";
        pstm = conn.prepareStatement(str);
        pstm.setString(1, dayName);
        pstm.setInt(2,personId);
        pstm.setInt(3, dayId);
        rs = pstm.executeQuery();
        cm.addRowsToTable(tableModel,rs);
    }

    private int getIdFromName(String name, String table)
            throws SQLException {
        String colName = cm.getNameOfColumnWithName(table);
        String colId = cm.getNameOfColumnWithId(table);
        pstm = conn.prepareStatement("select " + colId + " from " + table + " where " + colName + " = (?)");
        pstm.setString(1,name);
        rs = pstm.executeQuery();
        rs.next();
        return rs.getInt(colId);
    }

    private void showTasksWithNoDay()
            throws SQLException {
        DefaultTableModel tableModel = getTableModel("tasks");
        tableModel.setRowCount(0);

        int personRow = TasksMainApp.vt.listOfPeople.getSelectedRow();
        if(personRow == -1) return;
        String personName = TasksMainApp.vt.listOfPeople.getValueAt(personRow, 0).toString();
        int personId = getIdFromName(personName, "people");

        String str = "select task_name, day, done " +
                "from tasks join tasks_and_week on tasks.task_id = tasks_and_week.task_id " +
                "join week on tasks_and_week.day_id = week.day_id " +
                "where tasks.task_id in (select task_id from tasks_and_people where person_id = (?)) " +
                "order by week.day_id, tasks.task_id;";
        pstm = conn.prepareStatement(str);
        pstm.setInt(1, personId);
        rs = pstm.executeQuery();
        cm.addRowsToTable(tableModel,rs);
    }

    protected void setTaskDone(ActionEvent actionEvent) {
        int row = TasksMainApp.vt.listOfTasks.getSelectedRow();
        if(row == -1) return;
        String task_name = TasksMainApp.vt.listOfTasks.getValueAt(row,0).toString();
        String day = TasksMainApp.vt.listOfTasks.getValueAt(row,1).toString();

        try {
            startConnection();
            int task_id = getIdFromName(task_name,"tasks");
            int day_id = getIdFromName(day, "week");

            pstm = conn.prepareStatement("update tasks_and_week set done = 'True' where " +
                    "task_id = ? and day_id = ?;");
            pstm.setInt(1,task_id);
            pstm.setInt(2,day_id);
            pstm.executeUpdate();
            showTasks(null);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    protected void resetDoneTasks(ActionEvent actionEvent) {
        try {
            startConnection();
            pstm = conn.prepareStatement("update tasks_and_week set done = 'False' where 1=1;");
            pstm.executeUpdate();
            showTasks(null);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    protected void goToTaskManager(ActionEvent actionEvent) {
        TasksMainApp.vt.setVisible(false);
        TasksMainApp.tm.setVisible(true);
    }

    private void startConnection()
            throws SQLException {
        conn = SQLBasics.startConnection();
    }

    private void closeSQLObjects() {
        SQLBasics.closeSQLObjects(rs, null, pstm, conn);
    }
}
