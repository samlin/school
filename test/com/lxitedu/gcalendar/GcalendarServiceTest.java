package com.lxitedu.gcalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gdata.data.Person;
import com.google.gdata.data.calendar.CalendarEntry;

public class GcalendarServiceTest {
  // @Test
  GcalendarService gcalendarService = new GcalendarService();

  @Test
  public void testCreateEvent() {
    List<Course> eventList = getEventList();
    // Calendar beginCalendar = new GregorianCalendar(2010, 5 - 1, 12);
    Calendar beginCalendar = new GregorianCalendar();
    // eventList = gcalendarService.getCourseList();
    String calendarName = "samlinTestCalendar1";
    // CalendarEntry calendar = gcalendarService.createCalendar(calendarName);
    CalendarEntry selectCalendar = gcalendarService.getCalendarEntryByCalendarName(calendarName);
    List<Person> contributors = selectCalendar.getContributors();
    System.out.println("GcalendarServiceTest.testCreateEvent()" + selectCalendar);
    // gcalendarService.createCalendarEventFromList(eventList, beginCalendar,
    // calendar);
  }

  // @Test
  public void testCreateCanendar() {
    String calendarName = "samlinTestCalendar1";
    CalendarEntry calendar = gcalendarService.createCalendar(calendarName);
    Assert.assertEquals(calendarName, calendar.getTitle().getPlainText());

  }

  // @Test
  public void testgetOwnCalendarList() throws Exception {
    Assert.assertTrue(gcalendarService.getOwnCalendarList().size() > 0);
  }

  // @Test
  public void testDeleteOwnCalendar() throws Exception {
    Object calendarName = "C0901";
    gcalendarService.deleteCalendarFrom(calendarName);
  }

  private List<Course> getEventList() {
    List<Course> eventList = new LinkedList<Course>();
    Course lxitEvent = new Course();
    lxitEvent.setName("java����֪ʶ��");
    lxitEvent.setContent("������java�����Ŀγ�����");
    lxitEvent.setDays(2);
    eventList.add(lxitEvent);
    lxitEvent = new Course();
    lxitEvent.setName("java�߼�");
    lxitEvent.setContent("�����Ǹ߼��γ̵Ŀγ�����");
    lxitEvent.setDays(3);
    eventList.add(lxitEvent);
    lxitEvent = new Course();
    lxitEvent.setName("java����γ�");
    lxitEvent.setContent("�����Ǹ߼��γ̵Ŀγ�����");
    lxitEvent.setDays(4);
    eventList.add(lxitEvent);
    return eventList;
  }
}
