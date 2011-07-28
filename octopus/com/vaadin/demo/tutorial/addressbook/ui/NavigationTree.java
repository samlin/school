package com.vaadin.demo.tutorial.addressbook.ui;

import java.util.List;

import com.lxitedu.dao.DBManager;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.demo.tutorial.addressbook.AddressBookApplication;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

@SuppressWarnings("serial")
public class NavigationTree extends Tree {
  public static final String LXIT_TREE_ITEM_PREFIX_CLASS = "prefixClass";
  public static final Object SHOW_ALL                    = "Show all";
  public static final Object SEARCH                      = "Search";
  private Object             hw_PROPERTY_NAME            = "name";
  public static String       LXIT_CLASS_ITEM_ID          = "班级管理";        ;
  public static String       COURSE_ITEM_ID              = "课程管理";        ;

  public NavigationTree(AddressBookApplication app) {
    addItem(SHOW_ALL);
    addItem(SEARCH);

    // setChildrenAllowed(ClassObject, true);
    HierarchicalContainer hwContainer = new HierarchicalContainer();
    hwContainer.addContainerProperty(hw_PROPERTY_NAME, String.class, null);
    hwContainer.addItem(SHOW_ALL);
    hwContainer.addItem(SEARCH);
    Item addItem = hwContainer.addItem(LXIT_CLASS_ITEM_ID);
    List<String> list = DBManager.getAllClassName();
    hwContainer.setChildrenAllowed(addItem, true);
    Item item = null;
    for (String className : list) {
      // Add new item
      item = hwContainer.addItem(className);
      // Add name property for item
      item.getItemProperty(hw_PROPERTY_NAME).setValue(LXIT_TREE_ITEM_PREFIX_CLASS + className);

      // addItem.getItemProperty("name").setValue(TREE_ITEM_PREFIX_CLASS +
      // className);
      hwContainer.setParent(className, LXIT_CLASS_ITEM_ID);
      // addItem.addItemProperty(className, property);
      // addItem.getItemProperty(string).setValue(string);

    }
    hwContainer.addItem(COURSE_ITEM_ID);
    setContainerDataSource(hwContainer);
    setChildrenAllowed(SHOW_ALL, false);

    /*
     * We want items to be selectable but do not want the user to be able to
     * de-select an item.
     */
    setSelectable(true);
    setNullSelectionAllowed(false);

    // Make application handle item click events
    addListener((ItemClickListener) app);

  }
}
