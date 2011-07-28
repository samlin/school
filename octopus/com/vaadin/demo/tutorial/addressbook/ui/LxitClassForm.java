package com.vaadin.demo.tutorial.addressbook.ui;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gdata.data.calendar.CalendarEntry;
import com.lxitedu.bean.LxitClass;
import com.lxitedu.gcalendar.Course;
import com.lxitedu.gcalendar.GcalendarService;
import com.lxitedu.service.ask.AskService;
import com.lxitedu.service.course.CourseServiceImpl;
import com.lxitedu.service.dokeos.DokeosLxitClassService;
import com.lxitedu.service.dokeos.DokeosUserService;
import com.lxitedu.service.jira.LxitJiraService;
import com.lxitedu.service.ucenter.UCenterService;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.demo.tutorial.addressbook.AddressBookApplication;
import com.vaadin.demo.tutorial.addressbook.data.LxitClassContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class LxitClassForm extends Form implements ClickListener {

  private Button save = new Button("Save", (ClickListener) this);
  private Button cancel = new Button("Cancel", (ClickListener) this);
  private Button edit = new Button("Edit", (ClickListener) this);
  private Button dokeos = new Button("Course", (ClickListener) this);
  private Button bbs = new Button("Bbs", (ClickListener) this);
  private Button ask = new Button("Ask", (ClickListener) this);
  private Button dayProblem = new Button("DayProblem", (ClickListener) this);
  private Button dayLog = new Button("dayLog", (ClickListener) this);
  private Button gCalendar = new Button("Calendar", (ClickListener) this);

  private TextField text = new TextField("日历开始顺序");
  // private CalendarEntry calendar = new CalendarEntry(requiredError, null,
  // null, requiredError, requiredError);

  private AddressBookApplication app;
  private boolean newContactMode = false;
  private LxitClass lxitClass = null;

  private PopupDateField datetime = new PopupDateField("Please select the starting time:");;

  public LxitClassForm(AddressBookApplication app) {
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
    footer.addComponent(ask);
    footer.addComponent(bbs);
    footer.addComponent(dokeos);
    footer.addComponent(dayProblem);
    footer.addComponent(dayLog);
    footer.addComponent(text);
    footer.addComponent(gCalendar);

    datetime.setValue(new java.util.Date());

    // Set the correct resolution
    datetime.setResolution(PopupDateField.RESOLUTION_DAY);

    // Add valuechangelistener
    datetime.addListener(this);
    datetime.setImmediate(true);
    footer.addComponent(datetime);

    footer.setVisible(false);

    setFooter(footer);

    /* Allow the user to enter new cities */

    /* Populate cities select using the cities in the data container */
    // FIXME
    // PersonContainer ds = app.getDataSource();
    // for (Iterator<Person> it = ds.getItemIds().iterator(); it.hasNext();)
    // {
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

    if (source == dokeos) {
      BeanItem bean = (BeanItem) getItemDataSource();
      LxitClass lxitClass = (LxitClass) bean.getBean();
      DokeosLxitClassService dokeosLxitClassService = new DokeosLxitClassService();
      DokeosUserService dokeosUserService = new DokeosUserService();
      if (dokeosLxitClassService.isClassNameExist(lxitClass.getName())) {
        app.getMainWindow().showNotification("班级已经创建", "班级已经创建", Notification.TYPE_WARNING_MESSAGE);
      } else {

        dokeosLxitClassService.add(lxitClass);
        app.getMainWindow().showNotification("恭喜" + "班级<" + lxitClass.getName() + ">创建创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
        dokeosUserService.addStudentByClassName(lxitClass.getName());
        app.getMainWindow().showNotification("恭喜", "班级<" + lxitClass.getName() + ">里的学生已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);

      }
    }

    if (source == dayProblem) {
      BeanItem bean = (BeanItem) getItemDataSource();
      LxitClass lxitClass = (LxitClass) bean.getBean();
      LxitJiraService lxitJiraservice = new LxitJiraService();
      lxitJiraservice.createDayProblem(lxitClass);
      app.getMainWindow().showNotification("恭喜", "班级<" + lxitClass.getName() + ">的每日问题工程已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
    }
    if (source == dayLog) {
      BeanItem bean = (BeanItem) getItemDataSource();
      LxitClass lxitClass = (LxitClass) bean.getBean();
      LxitJiraService lxitJiraservice = new LxitJiraService();
      lxitJiraservice.createGroup(lxitClass.getId());
      lxitJiraservice.createStudnetAndGroup(lxitClass);
      lxitJiraservice.createDayLogPermissionsAndScheme(lxitClass.getId());
      lxitJiraservice.createDayLogProject(lxitClass.getId());
      app.getMainWindow().showNotification("恭喜", "班级<" + lxitClass.getName() + ">的日志工程已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
    }
    if (source == ask) {
      BeanItem bean = (BeanItem) getItemDataSource();
      LxitClass lxitClass = (LxitClass) bean.getBean();
      AskService lxitJiraservice = new AskService();
      lxitJiraservice.addUsersByClassName(lxitClass.getName());
      ;
      app.getMainWindow().showNotification("恭喜", "班级<" + lxitClass.getName() + ">的提问系统已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
    }
    if (source == bbs) {
      BeanItem bean = (BeanItem) getItemDataSource();
      LxitClass lxitClass = (LxitClass) bean.getBean();
      UCenterService uCenterService = new UCenterService();
      uCenterService.addUsersByClassId(lxitClass.getName());
      ;
      app.getMainWindow().showNotification("恭喜", "班级<" + lxitClass.getName() + ">的论坛系统已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
    }
    if (source == gCalendar) {
      DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
      String dateOut = dateFormatter.format(datetime.getValue());
      Date date = (Date) (datetime.getValue());
      app.getMainWindow().showNotification("恭喜", "班级<" + dateOut + ">的在线日历已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
      Calendar beginCalendar = new GregorianCalendar();
      beginCalendar.setTime(date);

      // return;
      BeanItem bean = (BeanItem) getItemDataSource();
      LxitClass lxitClass = (LxitClass) bean.getBean();
      GcalendarService lxitJiraservice = new GcalendarService();
      CalendarEntry calendar = lxitJiraservice.createCalendar("stand" + lxitClass.getName());
      app.getMainWindow().showNotification("恭喜", "班级<" + lxitClass.getName() + ">的在线日历已经创建成功", Notification.TYPE_HUMANIZED_MESSAGE);
      GcalendarService gcalendarService = new GcalendarService();
      // Calendar beginCalendar = new GregorianCalendar();
      CourseServiceImpl courseServiceImpl = new CourseServiceImpl();
      List<Course> eventList = courseServiceImpl.getList();
      gcalendarService.createCalendarEventFromList(eventList, beginCalendar, calendar);
    }

    if (source == save) {
      /* If the given input is not valid there is no point in continuing */
      if (!isValid()) {
        return;
      }

      commit();
      if (newContactMode) {
        Item addedItem = app.getLxitClassDataSource().addItem(lxitClass);
        setItemDataSource(addedItem);
        newContactMode = false;
      }
      BeanItem bean = (BeanItem) getItemDataSource();
      // DAOFactory.getStudentDAO().add((Student) bean.getBean());
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
      List<Object> orderedProperties = Arrays.asList(LxitClassContainer.NATURAL_COL_ORDER);
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
    dokeos.setVisible(readOnly);
    dayProblem.setVisible(readOnly);
    dayLog.setVisible(readOnly);
    text.setVisible(readOnly);
    gCalendar.setVisible(readOnly);
  }

  public void addContact() {
    // Create a temporary item for the form
    lxitClass = new LxitClass();
    setItemDataSource(new BeanItem(lxitClass));
    newContactMode = true;
    setReadOnly(false);
  }

}