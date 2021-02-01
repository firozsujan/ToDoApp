package com.task.todoapp.server.dbconnectionmanager;

import com.task.todoapp.AppData;
import com.task.todoapp.server.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbConnectionManager implements IDbConnectionManager {

    public DbConnectionManager() {
        Utilities.readPropertiesFile();
        if (AppData.DB_CONNECTION == null) {
            AppData.DB_CONNECTION = connection();
        }
    }

    public Connection connection() {

        String url = AppData.DB_URL;
        String user = AppData.DB_USER;
        String password = AppData.DB_PASSWORD;

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
