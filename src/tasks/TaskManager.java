package tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class TaskManager extends JFrame {

    protected JTextField taskName;
    protected JTable listOfTasks;
    protected JCheckBox[] peopleCheckbox;
    protected JCheckBox[] weekCheckbox;

    protected TaskManagerListener listener;

    TaskManager() {
        super("Task Manager");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 450);
        setVisible(false);
        setLocationRelativeTo(null);
        listener = new TaskManagerListener();

        addAddTasksLabel();
        addTaskNameTextField();
        addCreateTaskButton();
        addChangeTasksLabel();
        addChooseTaskLabel();
        addWhoCanDoItLabel();
        addWhenLabel();
        addTasksTable();
        addPeopleCheckboxes();
        addWeekCheckboxes();
        addCommitChangeButton();
        addDeleteTaskButton();
        addShowTasksButton();
    }

    private void addAddTasksLabel() {
        JLabel addTasks = new JLabel("Add tasks");
        addTasks.setFont(new Font(addTasks.getFont().getFontName(), Font.BOLD, 15));
        addTasks.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 3;
        c.insets = new Insets(10, 0, 10, 0);
        add(addTasks, c);
    }

    private void addTaskNameTextField() {
        taskName = new JTextField(20);

        var c = new GridBagConstraints();
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        add(taskName, c);
    }

    private void addCreateTaskButton() {
        JButton createTask = new JButton("Create a task");
        createTask.addActionListener(listener::createTask);

        var c = new GridBagConstraints();
        c.gridy = 1;
        c.gridx = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 5, 0, 0);
        add(createTask, c);
    }

    private void addChangeTasksLabel() {
        JLabel changeTasks = new JLabel("Change tasks");
        changeTasks.setFont(new Font(changeTasks.getFont().getFontName(), Font.BOLD, 15));
        changeTasks.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        c.insets = new Insets(10, 0, 10, 0);
        add(changeTasks, c);
    }

    private void addChooseTaskLabel() {
        JLabel chooseTaskLabel = new JLabel("Choose a task");
        chooseTaskLabel.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 3;
        c.gridx = 0;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 20, 0);
        add(chooseTaskLabel, c);
    }

    private void addWhoCanDoItLabel() {
        JLabel whoCanDoItLabel = new JLabel("Who can do it?");
        whoCanDoItLabel.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 3;
        c.gridx = 1;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 20, 0);
        add(whoCanDoItLabel, c);
    }

    private void addWhenLabel() {
        JLabel whenLabel = new JLabel("When should it be done?");
        whenLabel.setHorizontalAlignment(JLabel.CENTER);

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 3;
        c.gridx = 2;
        c.weightx = 1;
        c.insets = new Insets(10, 0, 20, 0);
        add(whenLabel, c);
    }

    private void addTasksTable() {
        listOfTasks = new JTable();
        TableModel dataModel = new DefaultTableModel() {
            String colNames[] = {"task_name"};
            public int getColumnCount() { return 1; }
            public String getColumnName(int index) {
                return colNames[index];
            }
        };

        listOfTasks = new JTable(dataModel);
        listOfTasks.setTableHeader(null);
        listOfTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selMod = listOfTasks.getSelectionModel();
        selMod.addListSelectionListener(listener::reactToListSelection);

        JScrollPane scrollpane = new JScrollPane(listOfTasks);
        scrollpane.setPreferredSize(new Dimension(130,170));

        var c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 4;
        c.gridx = 0;
        c.insets = new Insets(0, 10, 0, 10);
        add(scrollpane, c);
    }

    private void addPeopleCheckboxes() {
        Container peopleContainer = new Container();
        peopleCheckbox = new JCheckBox[listener.countPeople()];
        peopleContainer.setLayout(new GridLayout(listener.countPeople(),1));
        listener.createListOfPeopleOrWeek(peopleContainer, peopleCheckbox, "people");

        var c = new GridBagConstraints();
        c.gridy = 4;
        c.gridx = 1;
        add(peopleContainer, c);
    }

    private void addWeekCheckboxes() {
        Container weekContainer = new Container();
        weekCheckbox = new JCheckBox[7];
        weekContainer.setLayout(new GridLayout(7,1));
        listener.createListOfPeopleOrWeek(weekContainer, weekCheckbox, "week");

        var c = new GridBagConstraints();
        c.gridy = 4;
        c.gridx = 2;
        add(weekContainer, c);
    }

    private void addCommitChangeButton() {
        JButton commitChange = new JButton("Commit changes");
        commitChange.addActionListener(listener::changeTask);

        var c = new GridBagConstraints();
        c.gridy = 5;
        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(10, 0, 0, 5);
        add(commitChange, c);
    }

    private void addDeleteTaskButton() {
        JButton deleteTask = new JButton("Delete a task");
        deleteTask.addActionListener(listener::deleteTask);

        var c = new GridBagConstraints();
        c.gridy = 5;
        c.gridx = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 5, 0, 0);
        add(deleteTask, c);
    }

    private void addShowTasksButton() {
        JButton showTasks = new JButton("Go to showing tasks");
        showTasks.addActionListener(listener::goToViewOfTasks);

        var c = new GridBagConstraints();
        c.gridy = 6;
        c.gridx = 2;
        c.weighty=1;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(20, 0, 5, 5);
        add(showTasks, c);
    }
}
