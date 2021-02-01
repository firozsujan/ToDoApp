package com.task.todorest.server.dbconnectionmanager;

import java.sql.Connection;

public interface IDbConnectionManager {
	public Connection connection();
}
