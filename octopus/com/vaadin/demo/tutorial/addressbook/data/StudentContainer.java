package com.vaadin.demo.tutorial.addressbook.data;

import java.io.Serializable;
import java.util.List;

import com.lxitedu.bean.Student;
import com.lxitedu.dao.DAOFactory;
import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class StudentContainer extends BeanItemContainer<Student> implements Serializable {

  /**
   * Natural property order for Person bean. Used in tables and forms.
   */
  public static final Object[] NATURAL_COL_ORDER   = new Object[] { "id", "classId", "name", "sex", "nativePlace",
      "education", "nation", "birth", "telephone", "mobilePhone", "idCard", "homeAddress", "dateOfEnrollment" };

  /**
   * "Human readable" captions for properties in same order as in
   * NATURAL_COL_ORDER.
   */
  public static final String[] COL_HEADERS_ENGLISH = new String[] { "学号", "班级", "姓名", "性别", "祖籍", "学历", "国籍", "生日",
      "家里电话", "电话", "身份证", "家庭地址", "毕业日期"         };

  public StudentContainer() throws InstantiationException, IllegalAccessException {
    super(Student.class);
  }

  public static StudentContainer createWithTestData() {

    StudentContainer c = null;
    List<Student> studentList = DAOFactory.getStudentDAO().kauGetList();

    try {
      c = new StudentContainer();
      for (Student student : studentList) {
        c.addItem(student);
      }

    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return c;
  }

}
