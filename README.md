## ToDoApp

# To run the application
* clone the repository and open as a project 
* Create a MySql or Postgres Database named "tododb"
* There will be db dump in the dbDump folder for both Mysql and postgres database
* Please update application.properties accordingly in the resources folder
* run the application with mvn wildfly:run
* application will be run in the below url
* http://localhost:8080/todoapp/

## Technologies (userd)

* Vaadin as frontend framework
* For backend used Java EE
* rest api
* Used Mysql/Postgres database
* Wildfly(JBoss)
* Maven

## Challenges I faced
* I am new to vaadin. So I have started from their documentation. From Vaadin's official website I have started from getting started. From there I have selected the latest version of vaadin but found the sample project is with SpringBoot but from my understanding, I have to do it with JavaEE. So I have downgraded the vaadin version to Vaadin 8 and started working on it. After learning & working several days on vaadin 8 I realized that it could be done with vaadin 14 but I have run out of time. So I have completed it with Vaadin 8. Another challenge I found in local PostgreSql server authentication. So after completing the task I have to prepare the project for MySql. Now, this application is compatible with both Postgres and MySql. We had a release on 1st February that's why I have to do this task after the huge office work, but I have enjoyed this because of those challenges