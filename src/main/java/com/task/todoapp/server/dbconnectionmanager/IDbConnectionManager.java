package com.task.todoapp.server.dbconnectionmanager;

import java.sql.Connection;

public interface IDbConnectionManager {
	public Connection connection();
}
