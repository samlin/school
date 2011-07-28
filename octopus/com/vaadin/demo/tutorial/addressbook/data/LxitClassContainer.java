package com.vaadin.demo.tutorial.addressbook.data;

import java.io.Serializable;
import java.util.List;

import com.lxitedu.bean.LxitClass;
import com.lxitedu.dao.DAOFactory;
import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class LxitClassContainer extends BeanItemContainer<LxitClass> implements Serializable {

  /**
   * Natural property order for Person bean. Used in tables and forms.
   */
  public static final Object[] NATURAL_COL_ORDER   = new Object[] { "id", "name", "descs", "teacherId" };

  /**
   * "Human readable" captions for properties in same order as in
   * NATURAL_COL_ORDER.
   */
  public static final String[] COL_HEADERS_ENGLISH = new String[] { "班级ID", "班级名", "描述", "班主任" };

  public LxitClassContainer() throws InstantiationException, IllegalAccessException {
    super(LxitClass.class);
  }

  public static LxitClassContainer createWithTestData() {

    LxitClassContainer c = null;
    List<LxitClass> lxitClassList = DAOFactory.getLxitClassDAO().getList();

    try {
      c = new LxitClassContainer();
      for (LxitClass student : lxitClassList) {
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
