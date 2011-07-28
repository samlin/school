package com.lxitedu.gcalendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.ColorProperty;
import com.google.gdata.data.calendar.HiddenProperty;
import com.google.gdata.data.calendar.TimeZoneProperty;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class LxCalendar {
  private static URL             owncalendarsFeedUrl;
  private static final String    METAFEED_URL_BASE            = "http://www.google.com/calendar/feeds/";
  private static final String    OWNCALENDARS_FEED_URL_SUFFIX = "/owncalendars/full";
  private static CalendarService calendarService              = null;
  @Getter
  @Setter
  private String                 userName                     = "lxitproject@gmail.com";
  @Getter
  @Setter
  private String                 password                     = "hibernate";

  public CalendarEventEntry createEventWithFullUrl(CalendarEventEntry myEntry, String calendarIdUrl) {
    try {
      URL url = new URL(calendarIdUrl);
      return getCalendarService().insert(url, myEntry);
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ServiceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  

  public static int getCountDays(Calendar start, int addDays) {
    int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);
    int count = dayOfWeek + addDays;
    if (count <= 7) {
      return addDays;
    }

    if (count > 7 && count <= 12) {
      return addDays + 1;
    }

    if (count > 12 && count <= 17) {
      return addDays + 2;
    }
    if (count > 17 && count <= 22) {
      return addDays + 3;
    }
    if (count > 22 && count <= 27) {
      return addDays + 4;
    }
    if (count > 27 && count <= 32) {
      return addDays + 5;
    }
    if (count > 32 && count <= 37) {
      return addDays + 6;
    }
    if (count > 37 && count <= 42) {
      return addDays + 7;
    }
    if (count > 42 && count <= 47) {
      return addDays + 8;
    }
    if (count > 47 && count <= 52) {
      return addDays + 9;
    }
    if (count > 52 && count <= 57) {
      return addDays + 10;
    }
    if (count > 57 && count <= 62) {
      return addDays + 11;
    }

    return dayOfWeek + addDays;
  }

  public CalendarEntry createCalendar(String title) throws Exception {
    CalendarService service = getCalendarService();
    service.setUserCredentials(getUserName(), getPassword());
    owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + getUserName() + OWNCALENDARS_FEED_URL_SUFFIX);
    CalendarEntry calendar = new CalendarEntry();
    calendar.setTitle(new PlainTextConstruct(title));
    calendar.setSummary(new PlainTextConstruct("This calendar contains the practice schedule and game times."));
    calendar.setTimeZone(new TimeZoneProperty("America/Los_Angeles"));
    calendar.setHidden(HiddenProperty.FALSE);
    calendar.setColor(new ColorProperty("#2952A3"));
    calendar.addLocation(new Where("", "", "Oakland"));

    return service.insert(owncalendarsFeedUrl, calendar);
  }

  public CalendarService getCalendarService() {
    if (calendarService == null) {
      calendarService = new CalendarService("demo-CalendarFeedDemo-1");
      try {
        calendarService.setUserCredentials(getUserName(), getPassword());
      } catch (AuthenticationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return calendarService;
  }

  public CalendarEventEntry createEvent(CalendarEventEntry myEntry, String calendarID) throws Exception {

    URL eventFeedUrl = new URL("http://www.google.com/calendar/feeds/" + calendarID + "/private/full");
    // Send the request and receive the response:
    return getCalendarService().insert(eventFeedUrl, myEntry);
  }

  public List<CalendarEntry> getOwnCalendarList()   {
    // Send the request and receive the response:
    CalendarFeed resultFeed=null;
    try {
      owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + getUserName() + OWNCALENDARS_FEED_URL_SUFFIX);
      resultFeed = getCalendarService().getFeed(owncalendarsFeedUrl, CalendarFeed.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resultFeed.getEntries();
  }
}
