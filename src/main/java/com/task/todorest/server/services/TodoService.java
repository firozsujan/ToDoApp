package com.task.todorest.server.services;

import com.task.todorest.server.dao.ITodoDao;
import com.task.todorest.server.dao.TodoDao;
import com.task.todorest.server.entities.Todo;

import java.util.List;


public class TodoService implements ITodoService {

	private ITodoDao todoDao;

	@Override
	public List<Todo> findAll() {
		todoDao = new TodoDao();
		return todoDao.findAll();
	}

	@Override
	public boolean save(Todo todo) {
		todoDao = new TodoDao();
		return todoDao.save(todo);
	}

	@Override
	public Todo find(Long id) {
		todoDao = new TodoDao();
		return todoDao.find(id);
	}

	@Override
	public boolean update(Todo todo, Long id) {
		todoDao = new TodoDao();
		return todoDao.update(todo, id);
	}

	@Override
	public boolean delete(Long id) {
		todoDao = new TodoDao();
		return todoDao.delete(id);
	}
}
