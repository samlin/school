package com.lxitedu.dao.dokeos;

import com.lxitedu.bean.dokeos.DokeosLxitClass;

//this is samlin add 2010-5-5
public interface DokeosLxitClassDAO {

  boolean isClassNameExist(String name);

  void add(DokeosLxitClass dokeosLxitClass);

}
