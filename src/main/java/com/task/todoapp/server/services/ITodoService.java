package com.task.todoapp.server.services;

import com.task.todoapp.server.entities.Todo;

import java.util.List;


public interface ITodoService {
	
	public List<Todo> findAll();

    public boolean save(Todo todo);

    public Todo find(Long id);

    public boolean update(Todo todo, Long id);

    public boolean delete(Long id);
}
