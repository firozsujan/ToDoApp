package com.task.todoapp.server.dao;

import com.task.todoapp.AppData;
import com.task.todoapp.server.dbconnectionmanager.DbConnectionManager;
import com.task.todoapp.server.dbconnectionmanager.IDbConnectionManager;
import com.task.todoapp.server.entities.Todo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class TodoDao implements ITodoDao {

    IDbConnectionManager iDbConnectionManager;

    public void dbConnection() {
        iDbConnectionManager = new DbConnectionManager();
        AppData.DB_CONNECTION = iDbConnectionManager.connection();
    }

    @Override
    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<>();
        if (AppData.DB_CONNECTION == null) {
            dbConnection();
        }

        try (Statement st = AppData.DB_CONNECTION.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM todo")) {

            while (rs.next()) {
                Long id = rs.getLong(1);
                Long date = rs.getLong(2);
                String itemName = rs.getString(3);
                String description = rs.getString(4);

                Todo todo = new Todo();
                todo.setId(id);
                todo.setDate(date);
                todo.setItemName(itemName);
                todo.setDescription(description);

                todos.add(todo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public boolean save(Todo todo) {

        if (AppData.DB_CONNECTION == null) {
            dbConnection();
        }
        String INSERT_TODO_SQL;
        if (AppData.DATABASE.equalsIgnoreCase(AppData.DATABASE_MYSQL)) {
            INSERT_TODO_SQL = "INSERT INTO todo( date, item_name, description) VALUES ( ?, ?, ?)";
        } else {
            //for postgres database;
            INSERT_TODO_SQL = "INSERT INTO todo( id, date, item_name, description) VALUES ( nextval('serial'), ?, ?, ?)";
        }
        boolean ret = true;

        try (PreparedStatement preparedStatement = AppData.DB_CONNECTION.prepareStatement(INSERT_TODO_SQL)) {
            preparedStatement.setLong(1, todo.getDate());
            preparedStatement.setString(2, todo.getItemName());
            preparedStatement.setString(3, todo.getDescription());

            int saveStatus = preparedStatement.executeUpdate();
            if (saveStatus == 0)
                ret = false;
            else
                ret = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public Todo find(Long id) {
        if (AppData.DB_CONNECTION == null) {
            dbConnection();
        }
        Todo todo = null;
        String FIND_TODO_SQL = "SELECT * FROM todo WHERE id = " + id;

        try (Statement st = AppData.DB_CONNECTION.createStatement(); ResultSet rs = st.executeQuery(FIND_TODO_SQL)) {

            // Process the ResultSet object.
            if (rs.next()) {
                id = rs.getLong(1);
                Long date = rs.getLong(2);
                String itemName = rs.getString(3);
                String description = rs.getString(4);

                todo = new Todo();
                todo.setId(id);
                todo.setDate(date);
                todo.setItemName(itemName);
                todo.setDescription(description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }

    @Override
    public boolean update(Todo todo, Long id) {

        if (AppData.DB_CONNECTION == null) {
            dbConnection();
        }

        String UPDATE_TODO_SQL = "UPDATE todo SET item_name = ?, description = ?, date = ? WHERE id = ?;";

        boolean ret = true;

        try (PreparedStatement preparedStatement = AppData.DB_CONNECTION.prepareStatement(UPDATE_TODO_SQL)) {

            preparedStatement.setString(1, todo.getItemName());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setLong(3, todo.getDate());
            preparedStatement.setLong(4, todo.getId());

            // Execute the query or update query
            int saveStatus = preparedStatement.executeUpdate();
            if (saveStatus == 0)
                ret = false;
            else
                ret = true;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public boolean delete(Long id) {

        if (AppData.DB_CONNECTION == null) {
            dbConnection();
        }

        String DELETE_TODO_SQL = "DELETE FROM todo WHERE id = ?;";

        boolean ret = true;

        try (PreparedStatement preparedStatement = AppData.DB_CONNECTION.prepareStatement(DELETE_TODO_SQL)) {
            preparedStatement.setLong(1, id);

            // Execute the query or update query
            int saveStatus = preparedStatement.executeUpdate();
            if (saveStatus == 0)
                ret = false;
            else
                ret = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
