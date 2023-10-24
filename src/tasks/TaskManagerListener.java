package tasks;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class TaskManagerListener {

    private Connection conn = null;
    private Statement stm = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
    private boolean updatingList = false;
    private CommonMethods cm = new CommonMethods();

    protected void createTask(ActionEvent actionEvent) {
        tryBlock: try {
            startConnection();
            int id = getNextId("tasks");
            pstm = conn.prepareStatement("insert into tasks values (?, ?)");
            pstm.setInt(1, id);
            if(TasksMainApp.tm.taskName.getText().equals("")) {
                break tryBlock;
            }
            pstm.setString(2, TasksMainApp.tm.taskName.getText());
            pstm.executeUpdate();

            updateListOfTasks();
            TasksMainApp.tm.taskName.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    private int getNextId(String table)
            throws SQLException {
        stm = conn.createStatement();
        String colId = cm.getNameOfColumnWithId(table);
        rs = stm.executeQuery(("select max(" + colId + ") from " + table));
        rs.next();
        return rs.getInt(1)+1;
    }

    protected void updateListOfTasks() {
        updatingList = true;
        try {
            DefaultTableModel tableModel = (DefaultTableModel) TasksMainApp.tm.listOfTasks.getModel();
            tableModel.setRowCount(0);
            startConnection();
            stm = conn.createStatement();
            String str = ("select task_name from tasks order by task_id;");
            rs = stm.executeQuery(str);
            cm.addRowsToTable(tableModel, rs);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            closeSQLObjects();
            updatingList = false;
        }
    }

    protected int countPeople() {
        int result = -1;
        try {
            startConnection();
            stm = conn.createStatement();
            rs = stm.executeQuery("select count(*) from people");
            rs.next();
            result = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
        return result;
    }

    protected void createListOfPeopleOrWeek(Container con, JCheckBox[] cb, String table) {
        try {
            startConnection();
            stm = conn.createStatement();
            String colName = cm.getNameOfColumnWithName(table);
            rs = stm.executeQuery("select " + colName + " from " + table + ";");
            int i = 0;
            while(rs.next()) {
                String name = rs.getString(colName);
                cb[i] = new JCheckBox(name);
                con.add(cb[i]);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    protected void reactToListSelection(ListSelectionEvent listSelectionEvent) {
        if(updatingList) return;
        String selectedName = getSelectedItem();
        updateCheckboxes(selectedName);
    }

    private String getSelectedItem() {
       String result;
        JTable table = TasksMainApp.tm.listOfTasks;
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        if(row == -1) return "";
        result = tableModel.getValueAt(row,0).toString();
        return result;
    }

    protected void updateCheckboxes(String task) {
        try {
            startConnection();
            int id = getIdFromName(task, "tasks");

            updatePeopleOrWeekCheckboxes("people", id);
            updatePeopleOrWeekCheckboxes("week", id);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
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

    private void updatePeopleOrWeekCheckboxes(String table, int taskId)
            throws SQLException {
        String colId = cm.getNameOfColumnWithId(table);
        pstm = conn.prepareStatement("select " + colId + " from tasks_and_" + table + " where task_id = ?");
        pstm.setInt(1, taskId);
        rs = pstm.executeQuery();

        JCheckBox checkboxes[];
        if(table.equals("week"))  checkboxes = TasksMainApp.tm.weekCheckbox;
        else checkboxes = TasksMainApp.tm.peopleCheckbox;

        for(int i=0; i<checkboxes.length; i++) checkboxes[i].setSelected(false);
        while(rs.next()) {
            checkboxes[rs.getInt(1)-1].setSelected(true);
        }
    }

    protected void changeTask(ActionEvent actionEvent) {
        try {
            String task = getSelectedItem();
            startConnection();
            int id = getIdFromName(task, "tasks");

            updateTasksAndPeopleOrWeek("people", id);
            updateTasksAndPeopleOrWeek("week", id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    private void updateTasksAndPeopleOrWeek(String table, int taskId)
            throws SQLException {
        JCheckBox[] checkboxes;
        if(table.equals("people")) checkboxes = TasksMainApp.tm.peopleCheckbox;
        else checkboxes = TasksMainApp.tm.weekCheckbox;
        String colId = cm.getNameOfColumnWithId(table);

        for(int i=0; i<checkboxes.length; i++) {
            boolean b = checkboxes[i].isSelected();
            pstm = conn.prepareStatement("select count(*) from tasks_and_" + table + " where task_id = (?) and " + colId + " = (?)");
            pstm.setInt(1,taskId);
            pstm.setInt(2,(i+1));
            rs = pstm.executeQuery();
            rs.next();
            if(b && rs.getInt(1)==0) addToTask(("tasks_and_" + table), (i+1), taskId);
            if(!b && rs.getInt(1)==1) deleteFromTask(("tasks_and_" + table), (i+1), taskId);
        }
    }
    
    private void addToTask(String table, int checkbox_id, int task_id)
            throws SQLException {
        if(table.equals("tasks_and_week")) pstm = conn.prepareStatement("insert into " + table + " values (?,?,?);");
        else pstm = conn.prepareStatement("insert into " + table + " values (?,?);");
        pstm.setInt(1, task_id);
        pstm.setInt(2,checkbox_id);
        if(table.equals("tasks_and_week")) pstm.setString(3, "False");
        pstm.executeUpdate();
    }

    private void deleteFromTask(String table, int checkbox_id, int task_id)
            throws SQLException{
        String secondColName;
        if(table.equals("tasks_and_people")) secondColName = "person_id";
        else secondColName = "day_id";
        pstm = conn.prepareStatement("delete from " + table + " where task_id = (?) and " + secondColName + " = (?);");
        pstm.setInt(1, task_id);
        pstm.setInt(2,checkbox_id);
        pstm.executeUpdate();
    }

    protected void deleteTask(ActionEvent actionEvent) {
        try {
            startConnection();
            JTable table = TasksMainApp.tm.listOfTasks;
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int row = table.getSelectedRow();
            pstm = conn.prepareStatement("delete from tasks where task_name = ?");
            if(row == -1) return;
            pstm.setString(1, tableModel.getValueAt(row,0).toString());
            pstm.executeUpdate();
            updateListOfTasks();
            resetCheckboxes();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSQLObjects();
        }
    }

    private void resetCheckboxes() {
        JCheckBox[] cb = TasksMainApp.tm.peopleCheckbox;
        for(int i=0; i<cb.length;i++) cb[i].setSelected(false);
        cb = TasksMainApp.tm.weekCheckbox;
        for(int i=0; i<cb.length;i++) cb[i].setSelected(false);
    }

    protected void goToViewOfTasks(ActionEvent actionEvent) {
        TasksMainApp.tm.setVisible(false);
        TasksMainApp.vt.setVisible(true);
    }

    private void startConnection()
            throws SQLException {
        conn = SQLBasics.startConnection();
    }

    private void closeSQLObjects() {
        SQLBasics.closeSQLObjects(rs, stm, pstm, conn);
    }
}
