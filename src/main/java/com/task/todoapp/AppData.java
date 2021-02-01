package com.task.todoapp;

import java.sql.Connection;

/**
 * This class is for common static variables of Frontend
 */
public class AppData{

    public static final String DATABASE_MYSQL = "MYSQL";
    public static final String DATABASE_POSTGRES = "POSTGRES";

    public static String DATABASE = "";

    public static Connection DB_CONNECTION;

    public static String DB_URL = "";
    public static String DB_USER = "";
    public static String DB_PASSWORD = "";
}