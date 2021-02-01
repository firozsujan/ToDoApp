package com.task.todorest;

import java.sql.Connection;

/**
 * This class is for common static variables of Frontend
 */
public class AppData{

    public static final String DATABASE_MYSQL = "MYSQL";

    public static final String DATABASE_POSTGRES = "POSTGRES";

    public static final String DATABASE = DATABASE_MYSQL;


    public static Connection DB_CONNECTION;

    public static String URL_POSTGRESQL = "jdbc:postgresql://localhost:5432/tododb";

    public static String URL_MYSQL = "jdbc:mysql://localhost:3306/tododb";
    public static String MYSQL_DB_USER = "root";

    public static String POSTGRES_DB_USER =  "proit";
    public static String MYSQL_DB_PASSWORD = "";
    public static final String POSTGRES_DB_PASSWORD = "123456";
}