package com.lxitedu.gcalendar;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.common.base.StringUtil;
import com.lxitedu.course.dao.CourseDAO;
import com.lxitedu.course.dao.kauimpl.CourseDAOkauImpl;

public class GcalendarService {
  public CourseDAO courseDAO  = new CourseDAOkauImpl();
  LxCalendar       lxCalendar = new LxCalendar();

  public void createCalendarEventFromList(List<Course> eventList, Calendar beginCalendar, CalendarEntry calendarEntry) {
    Calendar calendar = beginCalendar;
    for (Course event : eventList) {
      if (event.getDays() == 0) {
        event.setDays(1);
      }
      CalendarEventEntry myEntry = new CalendarEventEntry();
      myEntry.setTitle(new PlainTextConstruct(event.getName()));
      myEntry.setContent(new PlainTextConstruct(event.getContent()));
      myEntry.setQuickAdd(false);
      DateTime startTime = new DateTime(calendar.getTime(), TimeZone.getDefault());

      calendar.add(Calendar.DATE, lxCalendar.getCountDays(calendar, event.getDays() - 1));
      DateTime endTime = new DateTime(calendar.getTime(), TimeZone.getDefault());

      When eventTimes = new When();
      eventTimes.setStartTime(startTime);
      eventTimes.setEndTime(endTime);
      myEntry.addTime(eventTimes);

      calendar.add(Calendar.DATE, 1);
      try {
        // String runId =
        // "http://www.google.com/calendar/feeds/lxitproject%40gmail.com/owncalendars/full/chuihj7jnp4pjjrarbibrdgnvo%40group.calendar.google.com";
        String runId = calendarEntry.getId();
        String revrser = StringUtils.reverse(runId);
        String temp = StringUtils.right(runId, revrser.indexOf("/"));

        String id = StringUtil.htmlEscape(temp);
        System.out.println("GcalendarService.createCalendarEventFromList():" + temp);
        lxCalendar.createEvent(myEntry, id);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  public List<Course> getCourseList() {
    return courseDAO.getList();
  }

  public CalendarEntry createCalendar(String calendarName) {
    CalendarEntry calendar = null;
    try {
      calendar = lxCalendar.createCalendar(calendarName);
      return calendar;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<CalendarEntry> getOwnCalendarList() {
    return lxCalendar.getOwnCalendarList();
  }

  public CalendarEntry getCalendarEntryByCalendarName(String calendarName) {
    List<CalendarEntry> list = lxCalendar.getOwnCalendarList();
    for (CalendarEntry calendarEntry : list) {
      if (calendarName.equals(calendarEntry.getTitle().getPlainText())) {
        return calendarEntry;
      }
    }
    return null;
  }

  public void deleteCalendarFrom(Object calendarName) {
    List<CalendarEntry> ownCalendarList = lxCalendar.getOwnCalendarList();
    for (CalendarEntry calendarEntry : ownCalendarList) {
      if (calendarEntry.getTitle().getPlainText().equals(calendarName)) {
        try {
          calendarEntry.delete();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (ServiceException e) {
          e.printStackTrace();
        }
      }
    }

  }

  public CalendarEntry createCalendar(String calendarName, String beginEventSeq) {
    CalendarEntry calendar = createCalendar(calendarName);

    List<Course> list = courseDAO.getListFromBeginSeq(beginEventSeq);
    // Fixme
    // Calendar calendar2 = createCalendarEventFromList(list, calendar);
    return null;
  }

}
