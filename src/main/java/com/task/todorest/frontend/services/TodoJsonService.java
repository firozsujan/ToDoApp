package com.task.todorest.frontend.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.task.todorest.frontend.AppDataFE;
import com.task.todorest.frontend.model.Todo;
import org.apache.deltaspike.core.api.config.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

/**
 */
@ApplicationScoped
public class TodoJsonService {

    @Inject
    @ConfigProperty(name = "weathermap.apikey")
    private String FIND_ALL_API = AppDataFE.BASE_API + "todos";
    private String FIND_API = AppDataFE.BASE_API + "todos";
    private String SAVE_API = AppDataFE.BASE_API + "todos";
    private String UPDATE_API = AppDataFE.BASE_API + "todos";
    private String DELETE_API = AppDataFE.BASE_API + "todos";

    private Client client;
    private WebTarget target;

    public List<Todo> findAll() {
        client = ClientBuilder.newClient();
        target = client.target(FIND_ALL_API);
        JsonArray responseJsonArray = target.request().accept(MediaType.APPLICATION_JSON).get(JsonArray.class);

        List<Todo> todos = new ArrayList<>();

        for (JsonObject jsonObject : responseJsonArray.getValuesAs(JsonObject.class)) {
            Todo todo = new Todo();
            todo.setId(Long.parseLong(jsonObject.get("id").toString()));
            todo.setItemName(jsonObject.getString("itemName"));
            todo.setDescription(jsonObject.getString("description"));
            todo.setDate(Long.parseLong(jsonObject.get("date").toString()));

            System.out.println("responseJsonArray: " + jsonObject.get("date"));

            todos.add(todo);

        }
        return todos;
    }

    public void save(Todo todo) {
        client = ClientBuilder.newClient();
        target = client.target(SAVE_API);
        try {
            Response responseJsonObject = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(todo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Todo find(Long id) {
        client = ClientBuilder.newClient();
        target = client.target(FIND_API + "/" + id);
        JsonObject responseJsonObject = target.request().accept(MediaType.APPLICATION_JSON).get(JsonObject.class);

        Todo todo = new Todo();
        todo.setId(Long.parseLong(responseJsonObject.get("id").toString()));
        todo.setItemName(responseJsonObject.getString("itemName"));
        todo.setDescription(responseJsonObject.getString("description"));
        todo.setDate(Long.parseLong(responseJsonObject.get("date").toString()));

        return todo;
    }

    public void update(Todo todo, Long id) {
        client = ClientBuilder.newClient();
        target = client.target(UPDATE_API + "/" + id);
        Response responseJsonObject = target.request().accept(MediaType.APPLICATION_JSON).put(Entity.json(todo));
    }

    public void delete(Long id) {

        client = ClientBuilder.newClient();
        target = client.target(DELETE_API + "/" + id);
        Response responseJsonObject = target.request().accept(MediaType.APPLICATION_JSON).delete();
    }
}