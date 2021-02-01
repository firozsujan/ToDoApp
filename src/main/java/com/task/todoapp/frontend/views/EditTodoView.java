package com.task.todoapp.frontend.views;

import com.task.todoapp.frontend.AppDataFE;
import com.task.todoapp.frontend.UtilitiesFE;
import com.task.todoapp.frontend.model.Todo;
import com.task.todoapp.frontend.services.TodoJsonService;
import com.vaadin.data.HasValue;
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
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;

/**
 * This is the Edit View of Todo
 */
public class EditTodoView extends VerticalLayout implements View {

    private Todo todo;
    private Long id;
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

    public EditTodoView(Navigator navigator) {
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

        todoJsonService = new TodoJsonService();
        if (id != null)
            todo = todoJsonService.find(id);

        Panel panel = new Panel();

        itemNameTextField = new TextField("Item Name");
        descriptionTextField = new TextField("Description");
        dateField = new DateField("Date");
        dateField.setDateFormat("dd-MM-yyyy");
        if (todo != null) {

            System.out.println("todo.getItemName() " + todo.getItemName() + " D: " + todo.getDescription() + " Date: "
                    + todo.getDate());

            itemNameTextField.setValue(todo.getItemName());
            descriptionTextField.setValue(todo.getDescription());
            dateField.setValue(UtilitiesFE.longToLocalDateConversion(todo.getDate()));
        }
        saveButton = new Button("Update");
        cancelButton = new Button("Cancel");

        itemNameTextField.addValueChangeListener(event -> checkItemNameText(event));
        descriptionTextField.addValueChangeListener(event -> checkDescriptionText(event));
        dateField.addValueChangeListener(event -> checkDateField(event));

        saveButton.setDisableOnClick(true);
        saveButton.isDisableOnClick();

        saveButton.addClickListener(event -> updateTodo(todo));

        cancelButton.addClickListener(event -> cancelTodo());

        itemNameTextField.setCaption("Item Name");
        descriptionTextField.setCaption("Description");
        dateField.setCaption("Date");
        dateField.setStyleName(ValoTheme.DATEFIELD_LARGE);

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



    private Object checkItemNameText(HasValue.ValueChangeEvent<String> event) {
        if (event.getValue() != null || !"".equalsIgnoreCase(event.getValue())) {
            isItemNameNotEmpty = true;
        } else {
            isItemNameNotEmpty = false;
        }
        activateSaveButton();
        return null;
    }

    private Object checkDescriptionText(HasValue.ValueChangeEvent<String> event) {
        if (event.getValue() != null || !"".equalsIgnoreCase(event.getValue())) {
            isDescriptionNotEmpty = true;
        } else {
            isDescriptionNotEmpty = false;
        }
        activateSaveButton();
        return null;
    }

    private Object checkDateField(HasValue.ValueChangeEvent<LocalDate> event) {
        if (event.getValue() != null) {
            isDateNotEmpty = true;
        } else {
            isDateNotEmpty = false;
        }
        activateSaveButton();
        return null;
    }

    private void activateSaveButton() {

        enableOrDisableButton(isItemNameNotEmpty, isDescriptionNotEmpty, isDateNotEmpty, saveButton);
    }

    /**
     * to check the textFields and Enable or disable save button if needed
     * @param isItemNameNotEmpty
     * @param isDescriptionNotEmpty
     * @param isDateNotEmpty
     * @param saveButton
     */
    public static void enableOrDisableButton(boolean isItemNameNotEmpty, boolean isDescriptionNotEmpty, boolean isDateNotEmpty, Button saveButton) {
        if (isItemNameNotEmpty && isDescriptionNotEmpty && isDateNotEmpty) {
            saveButton.setDisableOnClick(false);
            saveButton.isEnabled();
            saveButton.setEnabled(true);
        } else {
            saveButton.setDisableOnClick(true);
            saveButton.isDisableOnClick();
        }
    }

    private void addHeader() {
        Label header = new Label("Edit Todo");
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
        System.out.println("event.getParameters() : " + event.getParameters());
        if (event.getParameters() == null || event.getParameters().isEmpty()) {
            return;
        } else {
            id = Long.parseLong(event.getParameters());
            System.out.println("TodoID: " + id);
            if (AppDataFE.IS_REMOVED) {
                AppDataFE.IS_REMOVED = false;
                rootLayout.removeAllComponents();
                initViews();
            }
        }
    }

    private void cancelTodo() {
        navigator.navigateTo("");

    }

    private void updateTodo(Todo todo) {
        todo.setItemName(itemNameTextField.getValue());
        todo.setDescription(descriptionTextField.getValue());

        System.out.println("itemNameTextField: " + itemNameTextField.getValue());
        System.out.println("descriptionTextField.getValue(): " + descriptionTextField.getValue());
        System.out.println("dateField.getValue(): " + dateField.getValue());
        todo.setDate(UtilitiesFE.localDateToLongConversion(dateField.getValue()));

        todoJsonService.update(todo, todo.getId());
        navigator.navigateTo("");
    }
}