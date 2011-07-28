package com.vaadin.demo.tutorial.addressbook.ui;

import com.vaadin.ui.SplitPanel;

@SuppressWarnings("serial")
public class ListLxitClassView extends SplitPanel {
  public ListLxitClassView(LxitClassTable lxitClassTable, LxitClassForm lxitClassForm) {
    addStyleName("view");
    setFirstComponent(lxitClassTable);
    setSecondComponent(lxitClassForm);
    setSplitPosition(40);
  }
}