package com.vaadin.demo.tutorial.addressbook.ui;

import java.util.Arrays;
import java.util.List;

import com.lxitedu.bean.Student;
import com.lxitedu.dao.DAOFactory;
import com.lxitedu.service.jira.LxitJiraService;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.demo.tutorial.addressbook.AddressBookApplication;
import com.vaadin.demo.tutorial.addressbook.data.StudentContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class StudentForm extends Form implements ClickListener {

  private Button                 save           = new Button("Save", (ClickListener) this);
  private Button                 cancel         = new Button("Cancel", (ClickListener) this);
  private Button                 edit           = new Button("Edit", (ClickListener) this);
  private Button                 jira           = new Button("创建日志帐号", (ClickListener) this);

  private AddressBookApplication app;
  private boolean                newContactMode = false;
  private Student                newStudent     = null;

  public StudentForm(AddressBookApplication app) {
    this.app = app;

    /*
     * Enable buffering so that commit() must be called for the form before
     * input is written to the data. (Form input is not written immediately
     * through to the underlying object.)
     */
    setWriteThrough(false);

    HorizontalLayout footer = new HorizontalLayout();
    footer.setSpacing(true);
    footer.addComponent(save);
    footer.addComponent(cancel);
    footer.addComponent(edit);
    footer.addComponent(jira);
    footer.setVisible(false);

    setFooter(footer);

    /* Allow the user to enter new cities */

    /* Populate cities select using the cities in the data container */
    // FIXME
    // PersonContainer ds = app.getDataSource();
    // for (Iterator<Person> it = ds.getItemIds().iterator(); it.hasNext();) {
    // String city = (it.next()).getCity();
    // cities.addItem(city);
    // }

    /*
     * Field factory for overriding how the component for city selection is
     * created
     */
    setFormFieldFactory(new DefaultFieldFactory() {
      @Override
      public Field createField(Item item, Object propertyId, Component uiContext) {

        Field field = super.createField(item, propertyId, uiContext);

        field.setWidth("200px");
        return field;
      }
    });
  }

  public void buttonClick(ClickEvent event) {
    Button source = event.getButton();

    BeanItem bean = (BeanItem) getItemDataSource();
    if (source == save) {
      /* If the given input is not valid there is no point in continuing */
      if (!isValid()) {
        return;
      }

      commit();
      if (newContactMode) {
        Item addedItem = app.getDataSource().addItem(newStudent);
        setItemDataSource(addedItem);

        DAOFactory.getStudentDAO().add((Student) bean.getBean());
        newContactMode = false;
      } else {

        DAOFactory.getStudentDAO().update((Student) bean.getBean());
      }
      setReadOnly(true);
    } else if (source == cancel) {
      if (newContactMode) {
        newContactMode = false;
        /* Clear the form and make it invisible */
        setItemDataSource(null);
      } else {
        discard();
      }
      setReadOnly(true);
    } else if (source == edit) {
      setReadOnly(false);
    } else if (source == jira) {
      Student student = (Student) bean.getBean();
      LxitJiraService.createSingleUser(student);
      app.getMainWindow().showNotification("恭喜" + "用户<" + student.getName() + ">创建创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
    }
  }

  @Override
  public void setItemDataSource(Item newDataSource) {
    newContactMode = false;
    if (newDataSource != null) {
      List<Object> orderedProperties = Arrays.asList(StudentContainer.NATURAL_COL_ORDER);
      super.setItemDataSource(newDataSource, orderedProperties);

      setReadOnly(true);
      getFooter().setVisible(true);
    } else {
      super.setItemDataSource(null);
      getFooter().setVisible(false);
    }
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    save.setVisible(!readOnly);
    cancel.setVisible(!readOnly);
    edit.setVisible(readOnly);
    jira.setVisible(readOnly);
  }

  public void addContact() {
    // Create a temporary item for the form
    newStudent = new Student();
    setItemDataSource(new BeanItem(newStudent));
    newContactMode = true;
    setReadOnly(false);
  }

}