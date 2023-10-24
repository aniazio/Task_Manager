package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class ViewOfTasks extends JFrame {

    protected JTable listOfPeople;
    protected JTable listOfDays;
    protected JTable listOfTasks;
    protected ViewOfTasksListener listener;

    ViewOfTasks() {
        super("View of tasks");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        listener = new ViewOfTasksListener();

        addChoosePersonLabel();
        addChooseDayLabel();
        addPeopleTable();
        addDaysTable();
        addShowButton();
        addTasksTable();
        addDoneButton();
        addResetButton();
        addAddingTasksButton();
    }

    private void addChoosePersonLabel() {
        JLabel choosePerson = new JLabel("Choose a person");
        choosePerson.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 10, 0);
        add(choosePerson, c);
    }

    private void addChooseDayLabel() {
        JLabel chooseDay = new JLabel("Choose a day");
        chooseDay.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 10, 0);
        add(chooseDay, c);
    }

    private void addPeopleTable() {
        TableModel dataModelPeople = new DefaultTableModel() {
            String colNames[] = {"person_name"};
            public int getColumnCount() { return 1; }
            public String getColumnName(int index) {
                return colNames[index];
            }
        };

        listOfPeople = new JTable(dataModelPeople);
        listOfPeople.setTableHeader(null);
        listOfPeople.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollpanePeople = new JScrollPane(listOfPeople);
        scrollpanePeople.setPreferredSize(new Dimension(130,132));

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridx = 0;
        c.insets = new Insets(0, 10, 0, 10);
        add(scrollpanePeople, c);
    }

    private void addDaysTable() {
        TableModel dataModelDays = new DefaultTableModel() {
            String colNames[] = {"day_name"};
            public int getColumnCount() { return 1; }
            public String getColumnName(int index) {
                return colNames[index];
            }
        };

        listOfDays = new JTable(dataModelDays);
        listOfDays.setTableHeader(null);
        listOfDays.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollpaneDays = new JScrollPane(listOfDays);
        scrollpaneDays.setPreferredSize(new Dimension(130,132));

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridx = 1;
        c.insets = new Insets(0, 10, 0, 10);
        add(scrollpaneDays, c);
    }

    private void addShowButton() {
        JButton show = new JButton("Show tasks");
        show.addActionListener(listener::showTasks);

        var c = new GridBagConstraints();
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 10, 0);
        add(show, c);
    }

    private void addTasksTable() {
        TableModel dataModelTasks = new DefaultTableModel() {
            String colNames[] = {"Task","Day", "Done?"};
            public int getColumnCount() { return 3; }
            public String getColumnName(int index) {
                return colNames[index];
            }
        };

        listOfTasks = new JTable(dataModelTasks);
        listOfTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollpaneTasks = new JScrollPane(listOfTasks);
        scrollpaneTasks.setPreferredSize(new Dimension(130,150));

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 10, 0, 10);
        add(scrollpaneTasks, c);
    }

    private void addDoneButton() {
        JButton done = new JButton("Set a task as done");
        done.addActionListener(listener::setTaskDone);

        var c = new GridBagConstraints();
        c.gridy = 4;
        c.gridx = 0;
        c.insets = new Insets(10, 0, 10, 0);
        add(done, c);
    }

    private void addResetButton() {
        JButton reset = new JButton("Reset what is done");
        reset.addActionListener(listener::resetDoneTasks);

        var c = new GridBagConstraints();
        c.gridy = 4;
        c.gridx = 1;
        c.insets = new Insets(10, 0, 10, 0);
        add(reset, c);
    }

    private void addAddingTasksButton() {
        JButton addingTasks = new JButton("Go to adding tasks");
        addingTasks.addActionListener(listener::goToTaskManager);

        var c = new GridBagConstraints();
        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.weighty = 1;
        c.insets = new Insets(10, 0, 10, 10);
        add(addingTasks, c);
    }
}
