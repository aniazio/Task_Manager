package tasks;

import javax.swing.*;

public class TasksMainApp {

    static TaskManager tm;
    static ViewOfTasks vt;

    TasksMainApp() {
        tm = new TaskManager();
        vt = new ViewOfTasks();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TasksMainApp();
                tm.listener.updateListOfTasks();
                vt.listener.showListOfPeopleOrDays("people");
                vt.listener.showListOfPeopleOrDays("week");
            }
        });
    }

}
