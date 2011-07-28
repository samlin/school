package com.vaadin.demo.tutorial.addressbook.data;

import java.io.Serializable;
import java.util.List;

import com.lxitedu.gcalendar.Course;
import com.lxitedu.service.course.CourseServiceImpl;
import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class CourseContainer extends BeanItemContainer<Course> implements Serializable {

  /**
   * Natural property order for Person bean. Used in tables and forms.
   */
  public static final Object[] NATURAL_COL_ORDER   = new Object[] { "id", "name", "seq", "content", "goal", "days" };

  /**
   * "Human readable" captions for properties in same order as in
   * NATURAL_COL_ORDER.
   */
  public static final String[] COL_HEADERS_ENGLISH = new String[] { "课程id", "课程名称", "课程顺序", "课程内容", "课程目标", "课程天数" };

  public CourseContainer() throws InstantiationException, IllegalAccessException {
    super(Course.class);
  }

  public static CourseContainer createWithTestData() {

    CourseContainer c = null;
    List<Course> courseList = new CourseServiceImpl().getList();

    try {
      c = new CourseContainer();
      for (Course course : courseList) {
        c.addItem(course);
      }

    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return c;
  }

}
