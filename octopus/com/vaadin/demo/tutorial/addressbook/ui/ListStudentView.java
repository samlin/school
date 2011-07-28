package com.vaadin.demo.tutorial.addressbook.ui;

import com.vaadin.ui.SplitPanel;

@SuppressWarnings("serial")
public class ListStudentView extends SplitPanel {
  public ListStudentView(StudentTable studnetList, StudentForm studentForm) {
    addStyleName("view");
    setFirstComponent(studnetList);
    setSecondComponent(studentForm);
    setSplitPosition(40);
  }
}