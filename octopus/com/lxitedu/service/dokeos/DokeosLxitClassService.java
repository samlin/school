package com.lxitedu.service.dokeos;

import com.lxitedu.bean.LxitClass;
import com.lxitedu.bean.dokeos.DokeosLxitClass;
import com.lxitedu.dao.DAOFactory;
import com.lxitedu.dao.dokeos.DokeosLxitClassDAO;

//this is samlin add 2010-5-5
public class DokeosLxitClassService {
  private DokeosLxitClassDAO dokeosLxitClassDAO = DAOFactory.getDokeosLxitClassDAO();

  public boolean isClassNameExist(String name) {
    // TODO Auto-generated method stub
    return dokeosLxitClassDAO.isClassNameExist(name);
  }

  public void add(LxitClass lxitClass) {
    DokeosLxitClass dokeosLxitClass = new DokeosLxitClass();
    dokeosLxitClass.setCode(lxitClass.getId());
    dokeosLxitClass.setName(lxitClass.getName());
    dokeosLxitClassDAO.add(dokeosLxitClass);

  }
}
