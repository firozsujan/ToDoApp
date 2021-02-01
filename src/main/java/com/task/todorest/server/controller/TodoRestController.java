package com.task.todorest.server.controller;

import com.task.todorest.server.dao.ITodoDao;
import com.task.todorest.server.dao.TodoDao;
import com.task.todorest.server.entities.Todo;
import com.task.todorest.server.services.TodoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 *  rest controller for CRUD operation
 */
@Path("/todos")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoRestController {

    private ITodoDao todoDao;

    @GET
    public Response findAll() {
        todoDao = new TodoDao();
        return Response.ok().entity(todoDao.findAll()).build();
    }

    @POST
    public Response create(Todo todo, @Context UriInfo uriInfo) {
        todoDao = new TodoDao();
        boolean isSaved = todoDao.save(todo);

        return Response.ok().entity(isSaved).build();
    }

    @GET
    @Path("{id}")
    public Response find(@PathParam("id") Long id) {
        todoDao = new TodoDao();
        return Response.ok().entity(todoDao.find(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response update( Todo todo, @PathParam("id") Long id) {
        todoDao = new TodoDao();
        boolean isUpdated = todoDao.update(todo, id);

        return Response.ok().entity(isUpdated).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        todoDao = new TodoDao();
        todoDao.delete(id);
        return Response.noContent().build();
    }
}
