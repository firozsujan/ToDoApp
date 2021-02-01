package com.task.todorest.server.dbconnectionmanager;

import com.task.todorest.AppData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbConnectionManager implements IDbConnectionManager {

    public DbConnectionManager() {
        if (AppData.DB_CONNECTION == null) {
            AppData.DB_CONNECTION = connection();
        }
    }

    public Connection connection() {

        String url = "";
        String user = "";
        String password = "";


        if (AppData.DATABASE.equalsIgnoreCase(AppData.DATABASE_MYSQL)) {
            url = AppData.URL_MYSQL;
            user = AppData.MYSQL_DB_USER;
            password = AppData.MYSQL_DB_PASSWORD;
        } else {
            url = AppData.URL_POSTGRESQL;
            user = AppData.POSTGRES_DB_USER;
            password = AppData.POSTGRES_DB_PASSWORD;
        }

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        try {
            Connection con = DriverManager.getConnection(url, props);
            AppData.DB_CONNECTION = con;
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(DbConnectionManager.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return AppData.DB_CONNECTION;
    }
}
