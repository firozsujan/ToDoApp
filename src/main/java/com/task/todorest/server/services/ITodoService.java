package com.task.todorest.server.services;

import com.task.todorest.server.entities.Todo;

import java.util.List;


public interface ITodoService {
	
	public List<Todo> findAll();

    public boolean save(Todo todo);

    public Todo find(Long id);

    public boolean update(Todo todo, Long id);

    public boolean delete(Long id);
}
