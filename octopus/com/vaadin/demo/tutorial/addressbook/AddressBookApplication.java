package com.vaadin.demo.tutorial.addressbook;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.demo.tutorial.addressbook.data.CourseContainer;
import com.vaadin.demo.tutorial.addressbook.data.LxitClassContainer;
import com.vaadin.demo.tutorial.addressbook.data.SearchFilter;
import com.vaadin.demo.tutorial.addressbook.data.StudentContainer;
import com.vaadin.demo.tutorial.addressbook.ui.CourseForm;
import com.vaadin.demo.tutorial.addressbook.ui.CourseTable;
import com.vaadin.demo.tutorial.addressbook.ui.HelpWindow;
import com.vaadin.demo.tutorial.addressbook.ui.ListCourseView;
import com.vaadin.demo.tutorial.addressbook.ui.ListLxitClassView;
import com.vaadin.demo.tutorial.addressbook.ui.ListStudentView;
import com.vaadin.demo.tutorial.addressbook.ui.LxitClassForm;
import com.vaadin.demo.tutorial.addressbook.ui.LxitClassTable;
import com.vaadin.demo.tutorial.addressbook.ui.LxitLoginForm;
import com.vaadin.demo.tutorial.addressbook.ui.NavigationTree;
import com.vaadin.demo.tutorial.addressbook.ui.SearchView;
import com.vaadin.demo.tutorial.addressbook.ui.SharingOptions;
import com.vaadin.demo.tutorial.addressbook.ui.StudentForm;
import com.vaadin.demo.tutorial.addressbook.ui.StudentTable;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class AddressBookApplication extends Application implements ClickListener, ValueChangeListener, ItemClickListener {

  private NavigationTree     tree                = new NavigationTree(this);

  private Button             newContact          = new Button("Add contact");
  private Button             search              = new Button("Search");
  private Button             share               = new Button("Share");
  private Button             help                = new Button("Help");
  private SplitPanel         horizontalSplit     = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);

  // Lazyly created ui references
  private ListStudentView    listView            = null;
  private SearchView         searchView          = null;
  private StudentTable        personList          = null;
  private StudentForm        studentForm         = null;
  private HelpWindow         helpWindow          = null;
  private SharingOptions     sharingOptions      = null;

  private ListLxitClassView  lxitClassView       = null;
  private LxitClassTable     lxitClassTable      = null;
  private LxitClassForm      lxitClassForm       = null;

  private ListCourseView     listCourseView      = null;
  private CourseTable        courseTable         = null;
  private CourseForm         courseForm          = null;

  private StudentContainer   dataSource          = StudentContainer.createWithTestData();

  private LxitClassContainer lxitClassDataSource = LxitClassContainer.createWithTestData();
  private CourseContainer    courseDataSource    = CourseContainer.createWithTestData();

  @Override
  public void init() {
    buildMainLayout();
    setMainComponent(getListView());
  }

  private void buildMainLayout() {
    setMainWindow(new Window("Lxit 智能管理平台"));

    setTheme("contacts");

    // loginSuccess();
    getMainWindow().setContent(new LxitLoginForm(this));
  }

  public void loginSuccess() {
    VerticalLayout layout = new VerticalLayout();
    layout.setSizeFull();

    layout.addComponent(createToolbar());
    layout.addComponent(horizontalSplit);
    layout.setExpandRatio(horizontalSplit, 1);

    horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);
    horizontalSplit.setFirstComponent(tree);

    getMainWindow().setContent(layout);
  }

  private HorizontalLayout createToolbar() {
    HorizontalLayout lo = new HorizontalLayout();
    lo.addComponent(newContact);
    lo.addComponent(search);
    lo.addComponent(share);
    lo.addComponent(help);

    search.addListener((ClickListener) this);
    share.addListener((ClickListener) this);
    help.addListener((ClickListener) this);
    newContact.addListener((ClickListener) this);

    search.setIcon(new ThemeResource("icons/32/folder-add.png"));
    share.setIcon(new ThemeResource("icons/32/users.png"));
    help.setIcon(new ThemeResource("icons/32/help.png"));
    newContact.setIcon(new ThemeResource("icons/32/document-add.png"));

    lo.setMargin(true);
    lo.setSpacing(true);

    lo.setStyleName("toolbar");

    lo.setWidth("100%");

    Embedded em = new Embedded("", new ThemeResource("images/logo.png"));
    lo.addComponent(em);
    lo.setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
    lo.setExpandRatio(em, 1);

    return lo;
  }

  private void setMainComponent(Component c) {
    horizontalSplit.setSecondComponent(c);
  }

  /*
   * View getters exist so we can lazily generate the views, resulting in faster
   * application startup time.
   */
  private ListStudentView getListView() {
    if (listView == null) {
      personList = new StudentTable(this);
      studentForm = new StudentForm(this);
      listView = new ListStudentView(personList, studentForm);
    }
    return listView;
  }

  private SearchView getSearchView() {
    if (searchView == null) {
      searchView = new SearchView(this);
    }
    return searchView;
  }

  private ListLxitClassView getLxitClassView() {
    if (lxitClassView == null) {
      lxitClassTable = new LxitClassTable(this);
      lxitClassForm = new LxitClassForm(this);
      lxitClassView = new ListLxitClassView(lxitClassTable, lxitClassForm);
    }
    return lxitClassView;
  }

  private ListCourseView getCourseView() {
    if (listCourseView == null) {
      courseTable = new CourseTable(this);
      courseForm = new CourseForm(this);
      listCourseView = new ListCourseView(courseTable, courseForm);
    }
    return listCourseView;
  }

  private HelpWindow getHelpWindow() {
    if (helpWindow == null) {
      helpWindow = new HelpWindow();
    }
    return helpWindow;
  }

  private SharingOptions getSharingOptions() {
    if (sharingOptions == null) {
      sharingOptions = new SharingOptions();
    }
    return sharingOptions;
  }

  public StudentContainer getDataSource() {
    return dataSource;
  }

  public LxitClassContainer getLxitClassDataSource() {
    return lxitClassDataSource;
  }

  public CourseContainer getCourseDataSource() {
    return courseDataSource;
  }

  public void buttonClick(ClickEvent event) {
    final Button source = event.getButton();

    if (source == search) {
      showSearchView();
    } else if (source == help) {
      showHelpWindow();
    } else if (source == share) {
      showShareWindow();
    } else if (source == newContact) {
      addNewContanct();
    }
  }

  private void showHelpWindow() {
    getMainWindow().addWindow(getHelpWindow());
  }

  private void showShareWindow() {
    getMainWindow().addWindow(getSharingOptions());
  }

  private void showListView() {
    setMainComponent(getListView());
  }

  private void showSearchView() {
    setMainComponent(getSearchView());
  }

  public void valueChange(ValueChangeEvent event) {
    Property property = event.getProperty();
    if (property == personList) {
      Item item = personList.getItem(personList.getValue());
      if (item != studentForm.getItemDataSource()) {
        studentForm.setItemDataSource(item);
      }
    }
    if (property == lxitClassTable) {
      Item item = lxitClassTable.getItem(lxitClassTable.getValue());
      if (item != lxitClassForm.getItemDataSource()) {
        lxitClassForm.setItemDataSource(item);
      }
    }
    if (property == courseTable) {
      Item item = courseTable.getItem(courseTable.getValue());
      if (item != courseForm.getItemDataSource()) {
        courseForm.setItemDataSource(item);
      }
    }
  }

  public void itemClick(ItemClickEvent event) {
    if (event.getSource() == tree) {
      Object itemId = event.getItemId();
      if (itemId != null) {
        if (NavigationTree.SHOW_ALL.equals(itemId)) {
          // clear previous filters
          getDataSource().removeAllContainerFilters();
          showListView();
        } else if (NavigationTree.SEARCH.equals(itemId)) {
          showSearchView();
        } else if (NavigationTree.LXIT_CLASS_ITEM_ID.equals(itemId)) {
          System.out.println("AddressBookApplication.itemClick() " + itemId);
          setMainComponent(getLxitClassView());
        } else if (NavigationTree.COURSE_ITEM_ID.equals(itemId)) {
          System.out.println("Course Item Chick() " + itemId);
          setMainComponent(getCourseView());
        } else if (itemId instanceof SearchFilter) {
          search((SearchFilter) itemId);
        } else if (event.getItem().getItemProperty("name").toString().startsWith(NavigationTree.LXIT_TREE_ITEM_PREFIX_CLASS)) {
          SearchFilter searchFilter = new SearchFilter("classId", itemId.toString(), "");
          // event.getItem().getItemProperty(id)
          search(searchFilter);
          // showSearchView();
        }
      }
    }
  }

  private void addNewContanct() {
    showListView();
    studentForm.addContact();
  }

  public void search(SearchFilter searchFilter) {
    // clear previous filters
    getDataSource().removeAllContainerFilters();
    // filter contacts with given filter
    getDataSource().addContainerFilter(searchFilter.getPropertyId(), searchFilter.getTerm(), true, false);
    showListView();

    getMainWindow().showNotification(
        "Searched for " + searchFilter.getPropertyId() + "=*" + searchFilter.getTerm() + "*, found " + getDataSource().size() + " item(s).",
        Notification.TYPE_TRAY_NOTIFICATION);
  }

  public void saveSearch(SearchFilter searchFilter) {
    tree.addItem(searchFilter);
    tree.setParent(searchFilter, NavigationTree.SEARCH);
    // mark the saved search as a leaf (cannot have children)
    tree.setChildrenAllowed(searchFilter, false);
    // make sure "Search" is expanded
    tree.expandItem(NavigationTree.SEARCH);
    // select the saved search
    tree.setValue(searchFilter);
  }

}
