package com.task.todoapp.frontend.views;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("todotheme")
@CDIUI("")
public class MainView extends UI {
    Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("TodoList");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new TodoListView(navigator));
        navigator.addView("addTodo", new AddTodoView(navigator));
        navigator.addView("editTodo", new EditTodoView(navigator));

    }
}