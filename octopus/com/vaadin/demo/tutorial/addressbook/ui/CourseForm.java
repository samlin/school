package com.vaadin.demo.tutorial.addressbook.ui;

import java.util.Arrays;
import java.util.List;

import com.lxitedu.gcalendar.Course;
import com.lxitedu.service.course.CourseServiceImpl;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.demo.tutorial.addressbook.AddressBookApplication;
import com.vaadin.demo.tutorial.addressbook.data.CourseContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class CourseForm extends Form implements ClickListener {

  private Button                 save           = new Button("Save", (ClickListener) this);
  private Button                 cancel         = new Button("Cancel", (ClickListener) this);
  private Button                 edit           = new Button("Edit", (ClickListener) this);

  private AddressBookApplication app;
  private boolean                newContactMode = false;
  private Course                 course         = null;

  public CourseForm(AddressBookApplication app) {
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
        if (propertyId.equals("content")) {
          TextField textField = (TextField) field;
          field.setWidth("500px");
          textField.setRows(3);
          textField.setWordwrap(true);
          return textField;
        }
        if (propertyId.equals("goal")) {
          TextField textField = (TextField) field;
          field.setWidth("400px");
          textField.setRows(2);
          textField.setWordwrap(true);
          return textField;
        }

        field.setWidth("200px");
        return field;
      }
    });
  }

  public void buttonClick(ClickEvent event) {
    Button source = event.getButton();

    if (source == save) {
      /* If the given input is not valid there is no point in continuing */
      if (!isValid()) {
        return;
      }

      commit();
      BeanItem bean = (BeanItem) getItemDataSource();
      if (newContactMode) {
        Item addedItem = app.getCourseDataSource().addItem(course);
        setItemDataSource(addedItem);
        newContactMode = false;
        new CourseServiceImpl().add((Course) bean.getBean());
      } else {
        new CourseServiceImpl().update((Course) bean.getBean());

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
    }
  }

  @Override
  public void setItemDataSource(Item newDataSource) {
    newContactMode = false;
    if (newDataSource != null) {
      List<Object> orderedProperties = Arrays.asList(CourseContainer.NATURAL_COL_ORDER);
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
  }

  public void addContact() {
    // Create a temporary item for the form
    course = new Course();
    setItemDataSource(new BeanItem(course));
    newContactMode = true;
    setReadOnly(false);
  }

}