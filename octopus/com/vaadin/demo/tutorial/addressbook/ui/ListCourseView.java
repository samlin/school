package com.vaadin.demo.tutorial.addressbook.ui;

import com.vaadin.ui.SplitPanel;

@SuppressWarnings("serial")
public class ListCourseView extends SplitPanel {
  public ListCourseView(CourseTable courseTable, CourseForm courseForm) {
    addStyleName("view");
    setFirstComponent(courseTable);
    setSecondComponent(courseForm);
    setSplitPosition(40);
  }
}