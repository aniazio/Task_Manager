package tasks;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PeopleSwing {

    private JTextField input;
    private JTextField change;
    private JButton addButton, printButton, updateButton, deleteButton;
    protected JTable tableSwing;
    private JScrollPane scrollpane;
    private String selectedName;
    protected static PeopleSwing ps;
    private PeopleListener listener;

    PeopleSwing(){
        int width = 270;
        listener = new PeopleListener();

        input = new JTextField(20);
        change = new JTextField(20);

        addButton = new JButton("Insert into table");
        addButton.addActionListener(this::insertIntoTable);

        printButton = new JButton("Show Table");
        printButton.addActionListener(this::showTable);

        updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateTable);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteRowFromTable);

        setUpJTable(width);
        setUpJFrame(width);
    }

    private void setUpJFrame(int width) {
        JFrame jfrm = new JFrame("Adding people");
        jfrm.setSize(width+20, 350);
        jfrm.setLayout(new FlowLayout());
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setVisible(true);
        jfrm.setLocationRelativeTo(null);

        jfrm.add(input);
        jfrm.add(addButton);
        jfrm.add(printButton);
        jfrm.add(scrollpane);
        jfrm.add(change);
        jfrm.add(updateButton);
        jfrm.add(deleteButton);
    }

    private void setUpJTable(int width) {
        TableModel dataModel = new DefaultTableModel() {
            String colNames[] = {"imię"};
            public int getColumnCount() { return 1; }
            public String getColumnName(int index) {
                return colNames[index];
            }
        };

        tableSwing = new JTable(dataModel);
        TableColumnModel columnModel = tableSwing.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(width);

        scrollpane = new JScrollPane(tableSwing);
        scrollpane.setPreferredSize(new Dimension(width, 150));

        tableSwing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selMod = tableSwing.getSelectionModel();
        selMod.addListSelectionListener(this::addTextToChangeField);
    }

    private void insertIntoTable(ActionEvent e) {
        String name = input.getText();
        listener.insertIntoPeople(name);
        printButton.doClick();
        input.setText("");
    }

    private void showTable(ActionEvent e) {
        listener.selectAndPrintInSwing();
    }

    private void updateTable(ActionEvent e) {
        if(change.getText() == "") return;
        listener.updatePeople(selectedName, change.getText());
        selectedName = null;
        printButton.doClick();
    }

    private void deleteRowFromTable(ActionEvent e) {
        if(change.getText() == "") return;
        listener.deleteFromPeople(change.getText());
        printButton.doClick();
        change.setText("");
    }

    private void addTextToChangeField(ListSelectionEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel) tableSwing.getModel();
        int row = tableSwing.getSelectedRow();
        if(row == -1) return;
        selectedName = tableModel.getValueAt(row,0).toString();
        change.setText(selectedName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ps = new PeopleSwing();
                ps.listener.selectAndPrintInSwing();
            }
        });
    }
}
