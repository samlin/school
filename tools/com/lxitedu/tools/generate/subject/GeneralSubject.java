package com.lxitedu.tools.generate.subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lxitedu.tools.generate.observer.Observer;

public class GeneralSubject {
  private List<Observer> observerList = new ArrayList<Observer>();

  public void attach(Observer observer) {
    observerList.add(observer);
  }

  public void detach(Observer observer) {
    observerList.remove(observer);
  }

  public void toNotify(String initializeData[]) throws Exception {
    Iterator<Observer> it = observerList.iterator();
    while (it.hasNext()) {
      Thread.sleep(200);
      it.next().update(initializeData);
      System.out.println(it);
    }
  }
}
