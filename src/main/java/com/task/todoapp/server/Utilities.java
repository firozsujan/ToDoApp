package com.task.todoapp.server;

import com.task.todoapp.AppData;

import java.io.InputStream;
import java.util.Properties;

public class Utilities {

    /**
     * read application.properties file
     */
    public static void readPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream is = Utilities.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
            if (is != null) {
                AppData.DATABASE = properties.getProperty("datasource.name");
                AppData.DB_URL = properties.getProperty("datasource.url");
                AppData.DB_USER = properties.getProperty("datasource.username");
                AppData.DB_PASSWORD = properties.getProperty("datasource.password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
