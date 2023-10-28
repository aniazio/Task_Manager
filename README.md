# Task_Manager

## ENG

### Description

The program can be used to manage the performance of tasks in a team, for example for managing home jobs, which are done by family members during the week. Tasks must be completed on specific days of the week. 
They can be performed by different people. When someone does a task, others no longer have to do it. When the week is over, all tasks can be reset so that the program shows that they have not yet been completed.

Program was written in Java, but it also comunicate with database. Every table has its equivalent in database. SQL queries and commands were used in order to display data and modify it.

### Initialization

In order for the program to work properly, the tables in the SQL database must be created. To create the tables, firstly we should set the appropriate variable values in the SQLBasics class. Then the main method 
of the Initialization class should be run. WARNING: the program uses table names: tasks, people, week, tasks_and_people, tasks_and_week. These tables will be created in the database. The code may try to drop 
existing tables with the same name, or make changes in them. For security purposes, the methods that drop tables have been placed in the comment.

### How to use a program?

The program is displayed in two windows. User moves from one window to the other using "Go to..." buttons, which are located in the lower right corner. 

In the "View of tasks" window, to show tasks user shoud firstly select one person and one option from the "week" table, and then press the "Show tasks" button. To set a task as done, user should select it in 
the table, and then press the "Set a task as done" button. If someone does a task on a given day, others assigned to that task don't have to do it. The task is considered done. To reset everyone's tasks, 
press the "Reset what is done" button.

In the "Task Manager" window, you can add tasks and change their properties. To add a task, enter its unique name in the text field, and then press the "Create a task" button. To change the properties 
of the task, select the task from the table, and then select the options in the checkboxes. After making changes, confirm them by pressing the "Commit changes" button. To delete a task, select it from the table, 
and then press the "Delete a task" button.

Apart from that user can modify the data in the "people" table in a separate application, which can be opened by calling the main method of the PeopleSwing.class. In this application user can add new people to the table via the top text field and the "Insert into table" button. User can also modify and delete people in the table via aplication. To do this, they should select the appropriate row in the table, change the data using the lower text box, and then press the appropriate button.

## PL

### Opis

Program służy do zarządzania wykonywaniem zadań w zespole, np. zarządzaniem wykonywania obowiązków domowych przez członków rodziny w ciągu tygodnia. Zadania muszą być wykonane w określonych dniach tygodnia. Mogą 
je wykonywać różne osoby. Gdy jedna osoba zrobi zadanie, inna nie musi już go wykonywać. Po skończonym tygodniu można zresetować wszystkie zadania tak, by program pokazywał, że nie zostały one jeszcze w tym 
tygodniu wykonane.

W programie napisanym w Javie wykorzystano metody umożliwiające operacje na bazach danych. Wszystkie wyświetlane tabele mają swoje odpowiedniki w bazie danych. Do ich wyświetlania oraz modyfikacji użyto zapytań 
w języku SQL.

### Inicjalizacja

Aby program działał prawidłowo, muszą zostać utworzone odpowiednie tabele w bazie SQL. W celu utworzenia tabel należy w pierwszej kolejności ustawić odpowiednie wartości zmiennych w klasie SQLBasics. Następnie 
należy wywołać metodę main klasy Initialization. UWAGA: program używa nazw tabeli: tasks, people, week, tasks_and_people, tasks_and_week. Tabele te zostaną utworzone w bazie danych. Kod może próbować usunąć 
istniejące tebele o tej samej nazwie, albo wprowadzać w nich zmiany. Dla celów bezpieczeństwa metody powodujące usunięcie tabel zostały umieszczone w komentarzu.

### Jak używać?

Program jest wyświetlany w dwóch oknach. Od jednego okna do drugiego przechodzimy za pomocą odpowiednich przycisków "Go to..." umiejszczonych w prawym dolnym rogu. 

W oknie "View of tasks" należy wybrać jedną osobę oraz jedną opcję z tabeli "week", a następnie nacisnąć przycisk "Show tasks". Żeby zaznaczyć zadanie jako wykonanie należy zaznaczyć je w tabeli, a następnie 
wcisnąć przycisk "Set a task as done". Jeśli jedna osoba zrobi zadanie w danym dniu, to inne osoby przypisane do tego zadania nie muszą go wykonywać. Zadanie jest uznane za zrobione. Aby zresetować zadania 
wszystkich osób, należy nacisnąć przycisk "Reset what is done".

W oknie "Task Manager" można dodawać zadania oraz zmieniać ich właściwości. Aby dodać zadanie należy wpisać jego unikalną nazwę w pole tekstowe, a następnie nacisnąć przycisk "Create a task". Aby zmienić 
właściwości zadania należy wybrać zadanie z tabelki, a następnie zaznaczyć interesujące nas opcje w checkboxach. Po dokonaniu zmian należy je potwierdzić, wciskając przycisk "Commit changes". Aby usunąć 
zadanie należy zaznaczyć je w tabeli, a następnie nacisnąć przycisk "Delete a task".

Oprócz tego można modyfikować zawartość tabeli "people" w osobnej aplikacji, którą otwiera się poprzez wywołanie metody main klasy PeopleSwing. W tej aplikacji można dodawać nowe osoby do tabeli za pomocą górnego pola tekstowego oraz przycisku "Insert into table". Za pomocą aplikacji można również modyfikować i usuwać osoby w tabeli. W tym celu należy zaznaczyć odpowiedni wiersz tabeli, zmienić dane w dolnym polu tekstowym, a następnie nacisnąć odpowiedni przycisk.
