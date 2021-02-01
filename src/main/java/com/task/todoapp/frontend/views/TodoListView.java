package com.task.todoapp.frontend.views;

import java.util.List;

import com.task.todoapp.frontend.AppDataFE;
import com.task.todoapp.frontend.UtilitiesFE;
import com.task.todoapp.frontend.model.Todo;
import com.task.todoapp.frontend.services.TodoJsonService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This is the List View of Todo. Also can delete from list by clicking done button.
 */
public class TodoListView extends VerticalLayout implements View {
	private Navigator navigator;

	private TodoJsonService todoJsonService;

	private VerticalLayout rootLayout;

	private Grid<Todo> todoListGridLayout;

	public TodoListView(Navigator navigator) {

		this.navigator = navigator;
		setSizeFull();
		setupLayout();
		addHeader();
		addBody();
		addTodoList();
		addComponents(rootLayout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		addTodoList();
	}

	private void setupLayout() {
		rootLayout = new VerticalLayout();
		rootLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		rootLayout.setStyleName(ValoTheme.LAYOUT_WELL);
		// Enable Responsive CSS selectors for the layout
		Responsive.makeResponsive(rootLayout);

	}

	private void addHeader() {
		Label header = new Label("Todo List");
		header.addStyleName(ValoTheme.LABEL_H1);
		rootLayout.addComponent(header);

	}

	private void addBody() {
		Button button = new Button("Add Todo", VaadinIcons.PLUS_SQUARE_LEFT_O);
		button.addClickListener(e -> {
			navigator.navigateTo("addTodo");
			AppDataFE.IS_REMOVED = true;
		});
		rootLayout.addComponents(button);
	}

	private void addTodoList() {
		if (todoListGridLayout == null) {
			todoListGridLayout = new Grid<>(Todo.class);
		}
		todoListGridLayout.setStyleName(ValoTheme.LAYOUT_CARD);
		todoJsonService = new TodoJsonService();
		List<Todo> todos = todoJsonService.findAll();
		rootLayout.setWidth("100%");

		todoListGridLayout.setItems(todos);
		todoListGridLayout.setWidth("80%");
		todoListGridLayout.removeAllColumns();
		todoListGridLayout.addColumn(Todo::getItemName).setCaption("Item Name");
		todoListGridLayout.addColumn(Todo::getDescription).setCaption("Description");
		todoListGridLayout.addColumn(todo -> UtilitiesFE.longToFormattedLocalDateConversion(todo.getDate()))
				.setCaption("Date(dd-mm-yyyy)");

		addIconColumn();

		rootLayout.addComponent(todoListGridLayout);

	}

	private void addIconColumn() {

		todoListGridLayout.addComponentColumn(todo -> {
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			Button edit = new Button("Edit",VaadinIcons.EDIT);
			edit.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			edit.addClickListener(e -> {
				editTodo(todo);
			});
			Button done = new Button("Done",VaadinIcons.CHECK);
			done.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			done.addClickListener(e -> {
				doneTodo(todo);
			});

			horizontalLayout.addComponents(edit, done);

			return horizontalLayout;
		}).setCaption("Action");
	}

	private void doneTodo(Todo todo) {
		System.out.println("Deleted: " + todo.getItemName());
		todoJsonService = new TodoJsonService();
		todoJsonService.delete(todo.getId());
		addTodoList();
		todoListGridLayout.getDataProvider().refreshItem(todo);
	}

	private void editTodo(Todo todo) {
		AppDataFE.IS_REMOVED = true;
		navigator.navigateTo("editTodo/" + todo.getId());
	}

}
