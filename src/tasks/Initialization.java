package tasks;

import java.sql.*;

public class Initialization {
    static Connection conn = null;
    static Statement stm = null;
    static PreparedStatement pstm = null;
    static ResultSet rs = null;

    public static void main(String args[]) {
        /*
        dropTableIfExists("tasks_and_week");
        dropTableIfExists("tasks_and_people");
        dropTableIfExists("tasks");
        dropTableIfExists("week");
        dropTableIfExists("people");
         */

        createTasksTable();
        createPeopleTable();
        createWeekTable();
        createTasksAndPeopleTable();
        createTasksAndWeekTable();

        insertDataIntoWeekTable();
        insertExampleOfDataIntoPeopleTable();
        insertExampleOfDataIntoTasksTable();
    }

    private static void createTasksTable() {
        try {
            startConnection();
            String str = "CREATE TABLE `tasks` (\n" +
                    "  `task_id` int NOT NULL,\n" +
                    "  `task_name` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`task_id`),\n" +
                    "  UNIQUE KEY `task_name_UNIQUE` (`task_name`)\n" +
                    ")";
            stm = conn.createStatement();
            stm.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
            dropTableIfExists("tasks");
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void createPeopleTable() {
        try {
            startConnection();
            String str = "CREATE TABLE `people` (\n" +
                    "  `person_id` int NOT NULL,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`person_id`)\n" +
                    ")";
            stm = conn.createStatement();
            stm.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void createWeekTable() {
        try {
            startConnection();
            String str = "CREATE TABLE `week` (\n" +
                    "  `day_id` int NOT NULL,\n" +
                    "  `day` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`day_id`)\n" +
                    ")";
            stm = conn.createStatement();
            stm.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void createTasksAndWeekTable() {
        try {
            startConnection();
            String str = "CREATE TABLE `tasks_and_week` (\n" +
                    "  `task_id` int NOT NULL,\n" +
                    "  `day_id` int NOT NULL,\n" +
                    "  `done` varchar(10) NOT NULL DEFAULT 'False',\n" +
                    "  PRIMARY KEY (`day_id`,`task_id`),\n" +
                    "  KEY `task_FK_w_idx` (`task_id`),\n" +
                    "  CONSTRAINT `task_FK_w` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`) ON DELETE CASCADE,\n" +
                    "  CONSTRAINT `week_FK` FOREIGN KEY (`day_id`) REFERENCES `week` (`day_id`) ON DELETE CASCADE\n" +
                    ")";
            stm = conn.createStatement();
            stm.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void createTasksAndPeopleTable() {
        try {
            startConnection();
            String str = "CREATE TABLE `tasks_and_people` (\n" +
                    "  `task_id` int NOT NULL,\n" +
                    "  `person_id` int NOT NULL,\n" +
                    "  PRIMARY KEY (`task_id`,`person_id`),\n" +
                    "  KEY `people_FK_idx` (`person_id`),\n" +
                    "  CONSTRAINT `people_FK` FOREIGN KEY (`person_id`) REFERENCES `people` (`person_id`) ON DELETE CASCADE,\n" +
                    "  CONSTRAINT `task_FK_p` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`) ON DELETE CASCADE\n" +
                    ") ";
            stm = conn.createStatement();
            stm.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void insertDataIntoWeekTable() {
        try {
            startConnection();
            pstm = conn.prepareStatement("INSERT INTO week VALUES (?, ?)");
            conn.setAutoCommit(false);
            String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            for(int i=1; i<=7; i++) {
                pstm.setInt(1,i);
                pstm.setString(2,weekDays[i-1]);
                pstm.addBatch();
                pstm.clearParameters();
            }
            pstm.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void insertExampleOfDataIntoPeopleTable() {
        try {
            startConnection();
            pstm = conn.prepareStatement("INSERT INTO people VALUES (?, ?)");

            pstm.setInt(1,1);
            pstm.setString(2, "Mom");
            pstm.executeUpdate();
            pstm.clearParameters();

            pstm.setInt(1,2);
            pstm.setString(2, "Dad");
            pstm.executeUpdate();
            pstm.clearParameters();

            pstm.setInt(1,3);
            pstm.setString(2, "Ana");
            pstm.executeUpdate();
            pstm.clearParameters();

            pstm.setInt(1,4);
            pstm.setString(2, "Peter");
            pstm.executeUpdate();
            pstm.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void insertExampleOfDataIntoTasksTable() {
        try {
            startConnection();
            pstm = conn.prepareStatement("INSERT INTO tasks VALUES (?, ?)");

            pstm.setInt(1,1);
            pstm.setString(2, "Cleaning up");
            pstm.executeUpdate();
            pstm.clearParameters();

            pstm.setInt(1,2);
            pstm.setString(2, "Vacuuming");
            pstm.executeUpdate();
            pstm.clearParameters();

            pstm.setInt(1,3);
            pstm.setString(2, "Watering plants");
            pstm.executeUpdate();
            pstm.clearParameters();

            pstm.setInt(1,4);
            pstm.setString(2, "Washing dishes");
            pstm.executeUpdate();
            pstm.clearParameters();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void dropTableIfExists(String table) {
        try {
            startConnection();
            pstm = conn.prepareStatement("select * from INFORMATION_SCHEMA.INNODB_TABLES where name = (?);");
            pstm.setString(1,(SQLBasics.schema + "/" + table));
            rs = pstm.executeQuery();
            if(rs.next()) {
                String str = "drop table " + table + ";";
                stm = conn.createStatement();
                stm.executeUpdate(str);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeSQLObjects();
        }
    }

    private static void startConnection()
            throws SQLException {
        conn = SQLBasics.startConnection();
    }

    private static void closeSQLObjects() {
        SQLBasics.closeSQLObjects(rs, stm, pstm, conn);
    }

}
