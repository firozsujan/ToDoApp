package com.task.todoapp.frontend.views;

import java.time.LocalDate;

import com.task.todoapp.frontend.AppDataFE;
import com.task.todoapp.frontend.UtilitiesFE;
import com.task.todoapp.frontend.model.Todo;
import com.task.todoapp.frontend.services.TodoJsonService;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This is the Add View of Todo
 */
public class AddTodoView extends VerticalLayout implements View {


	private TodoJsonService todoJsonService;

	private Navigator navigator;
	private DateField dateField;

	private VerticalLayout rootLayout;

	private TextField itemNameTextField;

	private TextField descriptionTextField;

	private Button saveButton;

	private Button cancelButton;

	private static boolean isItemNameNotEmpty;
	private static boolean isDescriptionNotEmpty;
	private static boolean isDateNotEmpty;

	public AddTodoView(Navigator navigator) {
		this.navigator = navigator;
		setSizeFull();
		initViews();

		AppDataFE.IS_REMOVED = true;
	}

	private void initViews() {
		setupLayout();
		addHeader();
		addBody();
		addComponents(rootLayout);
	}

	private void addBody() {

		Panel panel = new Panel();
		// if (binder != null)
		// binder.removeBean();
		//
		// binder = new Binder<>();

		itemNameTextField = new TextField("Item Name");
		descriptionTextField = new TextField("Description");
		dateField = new DateField("Date");
		dateField.setStyleName(ValoTheme.DATEFIELD_LARGE);
		dateField.setDateFormat("dd-MM-yyyy");

		itemNameTextField.setCaption("Item Name");
		descriptionTextField.setCaption("Description");
		dateField.setCaption("Date");

		
		saveButton = new Button("Save");
		
		cancelButton = new Button("Cancel");
		

		itemNameTextField.addValueChangeListener(event -> checkItemNameText(event));
		descriptionTextField.addValueChangeListener(event -> checkDescriptionText(event));
		dateField.addValueChangeListener(event -> checkDateField(event));

		saveButton.setDisableOnClick(true);
		saveButton.isDisableOnClick();
	
		saveButton.addClickListener(event -> saveTodo());
		

		cancelButton.addClickListener(event -> cancelTodo());

		FormLayout formLayout = new FormLayout();

		formLayout.addComponents(itemNameTextField);

		formLayout.addComponents(descriptionTextField);

		formLayout.addComponent(dateField);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addComponents(saveButton, cancelButton);

		formLayout.addComponents(horizontalLayout);

		formLayout.setMargin(true);
		formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		panel.setContent(formLayout);

		rootLayout.addComponent(panel);

	}

	private Object checkItemNameText(ValueChangeEvent<String> event) {
		if (event.getValue() != null || !"".equalsIgnoreCase(event.getValue())) {
			isItemNameNotEmpty = true;
		} else {
			isItemNameNotEmpty = false;
		}
		activateSaveButton();
		return null;
	}

	private Object checkDescriptionText(ValueChangeEvent<String> event) {
		if (event.getValue() != null || !"".equalsIgnoreCase(event.getValue())) {
			isDescriptionNotEmpty = true;
		} else {
			isDescriptionNotEmpty = false;
		}
		activateSaveButton();
		return null;
	}

	private Object checkDateField(ValueChangeEvent<LocalDate> event) {
		if (event.getValue() != null) {
			isDateNotEmpty = true;
		} else {
			isDateNotEmpty = false;
		}
		activateSaveButton();
		return null;
	}

	private void activateSaveButton() {
		EditTodoView.enableOrDisableButton(isItemNameNotEmpty, isDescriptionNotEmpty, isDateNotEmpty, saveButton);
	}

	private void addHeader() {
		Label header = new Label("Add Todo");
		header.addStyleName(ValoTheme.LABEL_H1);
		rootLayout.addComponent(header);
	}

	private void setupLayout() {
		if (rootLayout == null) {
			rootLayout = new VerticalLayout();
		}
		rootLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		// Enable Responsive CSS selectors for the layout
		Responsive.makeResponsive(rootLayout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (AppDataFE.IS_REMOVED) {
			AppDataFE.IS_REMOVED = false;
			rootLayout.removeAllComponents();
			System.out.println("isRemoved: " + AppDataFE.IS_REMOVED);
			initViews();
		}
	}

	private void cancelTodo() {
		navigator.navigateTo("");
	}

	private void saveTodo() {
		Todo todo = new Todo();
		todo.setItemName(itemNameTextField.getValue());
		todo.setDescription(descriptionTextField.getValue());
		todo.setDate(UtilitiesFE.localDateToLongConversion(dateField.getValue()));

		System.out.println("todo.getItemName(): " + todo.getItemName());
		System.out.println("todo.getDescription(): " + todo.getDescription());
		System.out.println("todo.getDate(): " + todo.getDate());

		todoJsonService = new TodoJsonService();
		if (!"".equalsIgnoreCase(todo.getItemName()) && !"".equalsIgnoreCase(todo.getDescription())
				&& todo.getDate() != null) {
			todoJsonService.save(todo);
			navigator.navigateTo("");
		} else {
			Notification.show("Please fillup all fields");
		}

		
	}
}
