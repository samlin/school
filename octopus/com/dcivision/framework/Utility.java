/*
* @(#)Utility.java
*
* Copyright (c) 2003 DCIVision Ltd
* All rights reserved.
*
* This software is the confidential and proprietary information of DCIVision
* Ltd ("Confidential Information").  You shall not disclose such Confidential
* Information and shall use it only in accordance with the terms of the license
* agreement you entered into with DCIVision Ltd.
 */
package com.dcivision.framework;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
  Utility.java

  Common utility functions which used in systems.

    @author          Rollo Chan
    @company         DCIVision Limited
    @creation date   24/06/2003
    @version         $Revision: 1.42.6.4 $
    */

public class Utility {

  public static final String REVISION = "$Revision: 1.42.6.4 $";
  
  
  private Utility() {
  }

  /**
   * isEmpty
   *
   * Test to see whether input string is empty.
   *
   * @param   str A String
   * @return  True if it is empty; false if it is not.
   */
  public static boolean isEmpty(String str) {
    return(str == null || str.length() == 0 || str.trim().equals("") );
  }
  
  /**
   * isEmpty
   *
   * Test to see whether input string buffer is empty.
   *
   * @param   str A StringBuffer
   * @return  True if it is empty; false if it is not.
   */
  public static boolean isEmpty(StringBuffer stringBuffer) {
    return(stringBuffer == null || stringBuffer.length() == 0 || stringBuffer.toString().trim().equals("") );
  }

  /**
   * isEmpty
   *
   * Test to see whether input string is empty.
   *
   * @param   str A String
   * @return  True if it is empty; false if it is not.
   */
  public static boolean isEmpty(Object[] array) {
    return(array == null || array.length == 0);
  }

  /**
   * isEmpty
   *
   * Test to see whether input is empty.
   *
   * @param   StringArray A array
   * @return  True if it is empty; false if it is not.
   */
  public static boolean isEmpty(String[] array) {
    return(array == null || array.length == 0);
  }

  /**
   * isEmpty
   *
   * Test to see whether input is representing a NULL value.
   *
   * @param   val An Object
   * @return  True if it represents NULL; false if it is not.
   */
  public static boolean isEmpty(Object val) {
    return(val == null);
  }

  /**
   * isEmpty
   *
   * Test to see whether input is empty.
   *
   * @param   list A List
   * @return  True if it is empty; false if it is not.
   */
  public static boolean isEmpty(java.util.List list) {
    return(list == null || list.size() == 0);
  }

  public static boolean isHtmlNull(String str){
    if(isEmpty(str)||"null".equals(str.toLowerCase().trim())){
      return true;
    }
    return false;
  }
  /**
   * getLocaleByString
   *
   * Convert a string representation to a locale object.
   *
   * @param str String representation of that Locale
   * @return Locale after translation
   */

  /**
   * getCurrentTimestamp
   *
   * Returns current time in Timestamp object.
   *
   * @return  Timestamp object which representing the current time.
   */
  public static java.sql.Timestamp getCurrentTimestamp() {
    java.util.Calendar tmp = java.util.Calendar.getInstance();
    tmp.clear(java.util.Calendar.MILLISECOND);
    return(new java.sql.Timestamp(tmp.getTime().getTime()));
  }

  /**
   * getPropertyList
   *
   * Extract a list of properties of javabeans thro. using reflection
   *
   * @param    col the collection of javabeans to be extract
   * @param    property the property value of the javabean to be extracted
   * @throws   IllegalAccessException if error found in PropertyUtil
   * @throws   InvocationTargetException if error found in PropertyUtil
   * @throws   NoSuchMethodException if no such property
   * @return   A list of extracted properties
   */
 

  /**
   * getPropertyArray
   *
   * Retrive an array of object base on the collection thro. reflection
   * e.g groupList is a ArrayList of Group, then getArrayProperty(groupList,"ID") will return an List of ID values.
   *
   * @param col                         The collection of javabeans to be extract
   * @param propertyKey                 The property value key of the javabean to be extracted
   * @throws IllegalAccessException     If error found in PropertyUtil
   * @throws InvocationTargetException  If error found in PropertyUtil
   * @throws NoSuchMethodException      If no such property
   * @return                            Properties in array format
   */


  /**
   * getPropertyMap
   *
   * Retrive an map of object base on the collection thro. reflection
   * e.g groupList is a ArrayList of Group, then getArrayProperty(groupList,"ID") will return an List of ID values.
   *
   * @param col                           The collection of javabeans to be extract
   * @param propertyKey                   The property value key of the javabean to be extracted
   * @throws IllegalAccessException       If error found in PropertyUtil
   * @throws InvocationTargetException    If error found in PropertyUtil
   * @throws NoSuchMethodException        If no such property
   * @return                              Properties in map format
   */


  /**
   * getPropertyMapList
   *
   * Retrive an map of arraylist base on the collection thro. reflection
   * e.g groupList is a ArrayList of Group, then getPropertyMapList(groupList,"ID") will return an Map of ArrayList where which list contains objects with the same key.
   *
   * @param col                           The collection of javabeans to be extract
   * @param propertyKey                   The property value key of the javabean to be extracted
   * @throws IllegalAccessException       If error found in PropertyUtil
   * @throws InvocationTargetException    If error found in PropertyUtil
   * @throws NoSuchMethodException        If no such property
   * @return                              Properties in map format with arraylist as item
   */
 

  /**
   * Convert an Object Array into String Array by calling obj.toString()
   *
   * @param obj   The object array.
   * @return      The String Array representing that object array
   */
  public static String[] getStringArray(Object[] obj) {
    if(obj==null) {
      return null;
    }

    String[] strArray = new String[obj.length];
    for (int i=0; i<obj.length; i++) {
      strArray[i] = obj[i].toString();
    }
    return strArray;
  }

  /**
   *
   * @param String:  Query
   * @return String[]: Array of logical param
   */
  public static String[] getLogicalString(String query) {
    String andString[] = query.toUpperCase().split("\\s(AND|\\+)\\s");
    String orString[] = query.toUpperCase().split("\\sOR\\s");
    String notString[] = query.toUpperCase().split("\\s(\\-|NOT)\\s");
    String resultString[] = null;
    int count = 0;
    if (andString.length > 1) {
      for (int i=0;i< andString.length; i++) {
        andString[i] = "AND";
      }
      resultString = andString;
    } else if (orString.length > 1) {
      for (int i=0;i< orString.length; i++) {
        orString[i] = "OR";
      }
      resultString = orString;
    } else if (notString.length > 1) {
      for (int i=0;i< notString.length; i++) {
        notString[i] = "NOT";
      }
      resultString = notString;
    }

    return resultString;
  }

  /**
   * splitLogicalString
   *
   * @param query        Query
   * @return String[]    result splited array
   */
  public static String[] splitLogicalString(String query) {
    String andString[] = query.toUpperCase().split("\\s(AND|\\+)\\s");
    String orString[] = query.toUpperCase().split("\\sOR\\s");
    String notString[] = query.toUpperCase().split("\\s(\\-|NOT)\\s");
    String resultString[] = null;
    HashMap hm = new HashMap();
    if (andString.length > 1) {
      resultString = andString;
    } else if (orString.length > 1) {
      resultString = orString;
    } else if (notString.length > 1) {
      resultString = notString;
    } else {
      resultString = new String[1];
      resultString[0] = query;
    }
    return resultString;
  }

  /**
   * getList
   *
   * @param ary    The object array.
   * @return       The list contains all the object array elements.
   */
  public static List getList(Object[] ary) {
    if (ary==null) {
      return(null);
    }
    List result = new ArrayList();
    for (int i = 0; i < ary.length; i++) {
      result.add(ary[i]);
    }
    return(result);
  }

  /**
   * getStringArray
   *
   * @param ary    The object array.
   * @return       The list contains all the object array elements.
   */
  public static String[] getStringArray(List list) {
    if (list==null) {
      return(null);
    }
    String[] result = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      try {
        result[i] = (String)list.get(i);
      }catch (ClassCastException ce){
        result[i] = ((Integer)list.get(i)).toString();
      }
    }
    return(result);
  }

  /**
   * Returns Calendar converted from Timestamp.
   *
   * @param   inTime Source timestamp which to be converted.
   * @return  Calendar object which converted from input.
   */
  public static java.util.Calendar timestampToCalendar(java.sql.Timestamp inTime) {
    if (inTime == null) {
      return (null);
    }
    java.util.Calendar cal = java.util.Calendar.getInstance();
    cal.setTime(inTime);
    return (cal);
  }

  /**
   * Returns Timestamp converted from Calendar.
   *
   * @param   inCal Source calendar which to be converted.
   * @return  Timestamp object which converted from input.
   */
  public static java.sql.Timestamp calendarToTimestamp(java.util.Calendar inCal) {
    if (inCal == null) {
      return (null);
    }
    java.sql.Timestamp time = new java.sql.Timestamp(inCal.getTime().getTime());
    return (time);
  }

  /**
   * addYear - Returns the timestamp after adding certain amount of Year.
   *
   * @param   src Source timestamp.
   * @param   val Number of years going to add, can be negative number.
   * @return  Timestamp after adding certain amount of years.
   */
  public static java.sql.Timestamp addYear(java.sql.Timestamp src, int val) {
    java.util.Calendar tmpCal = timestampToCalendar(src);
    if (tmpCal == null) {
      return(null);
    }
    tmpCal.add(java.util.Calendar.YEAR, val);
    return (calendarToTimestamp(tmpCal));
  }

  /**
   * addMonth - Returns the timestamp after adding certain amount of Month.
   *
   * @param   src Source timestamp.
   * @param   val Number of months going to add, can be negative number.
   * @return  Timestamp after adding certain amount of months.
   */
  public static java.sql.Timestamp addMonth(java.sql.Timestamp src, int val) {
    java.util.Calendar tmpCal = timestampToCalendar(src);
    if (tmpCal == null) {
      return(null);
    }
    tmpCal.add(java.util.Calendar.MONTH, val);
    return (calendarToTimestamp(tmpCal));
  }

  /**
   * addDay - Returns the timestamp after adding certain amount of day.
   *
   * @param   src Source timestamp.
   * @param   val Number of days going to add, can be negative number.
   * @return  Timestamp after adding certain amount of days.
   */
  public static java.sql.Timestamp addDay(java.sql.Timestamp src, int val) {
    java.util.Calendar tmpCal = timestampToCalendar(src);
    if (tmpCal == null) {
      return(null);
    }
    tmpCal.add(java.util.Calendar.DAY_OF_MONTH, val);
    return (calendarToTimestamp(tmpCal));
  }

  /**
   * addHour - Returns the timestamp after adding certain amount of hours.
   *
   * @param   src Source timestamp.
   * @param   val Number of hours going to add, can be negative number.
   * @return  Timestamp after adding certain amount of hours.
   */
  public static java.sql.Timestamp addHour(java.sql.Timestamp src, int val) {
    java.util.Calendar tmpCal = timestampToCalendar(src);
    if (tmpCal == null) {
      return(null);
    }
    tmpCal.add(java.util.Calendar.HOUR, val);
    return (calendarToTimestamp(tmpCal));
  }

  /**
   * addMinute - Returns the timestamp after adding certain amount of minutes.
   *
   * @param   src Source timestamp.
   * @param   val Number of minutes going to add, can be negative number.
   * @return  Timestamp after adding certain amount of minutes.
   */
  public static java.sql.Timestamp addMinute(java.sql.Timestamp src, int val) {
    java.util.Calendar tmpCal = timestampToCalendar(src);
    if (tmpCal == null) {
      return(null);
    }
    tmpCal.add(java.util.Calendar.MINUTE, val);
    return (calendarToTimestamp(tmpCal));
  }

  /**
   * addSecond - Returns the timestamp after adding certain amount of seconds.
   *
   * @param   src Source timestamp.
   * @param   val Number of seconds going to add, can be negative number.
   * @return  Timestamp after adding certain amount of seconds.
   */
  public static java.sql.Timestamp addSecond(java.sql.Timestamp src, int val) {
    java.util.Calendar tmpCal = timestampToCalendar(src);
    if (tmpCal == null) {
      return(null);
    }
    tmpCal.add(java.util.Calendar.SECOND, val);
    return (calendarToTimestamp(tmpCal));
  }

  /**
   * Call this function to set the time section of a calendar
   *
   * @param inCalendar
   * @param hr
   * @param min
   * @param sec
   * @param milliSec
   */
  public static void setCalendarTime(Calendar inCalendar, int hr, int min, int sec, int milliSec) {
    if (inCalendar != null) {
      if (hr != -1) {
        inCalendar.set(Calendar.HOUR_OF_DAY, hr);
      }
      if (min != -1) {
        inCalendar.set(Calendar.MINUTE, min);
      }
      if (sec != -1) {
        inCalendar.set(Calendar.SECOND, sec);
      }
      if (milliSec != -1) {
        inCalendar.set(Calendar.MILLISECOND, milliSec);
      }
    }
  }

  /**
   * Call this function to parse the dateString into System Date format
   *
   * @param dateString               The date value string
   * @param dateFormat
   * @param min
   * @param sec
   * @param milliSec
   */

  /**
   * 1 day = 1*24*60*60*1000 millisecond.
   * 
   * @param dayValue
   * @return
   */
  public static int dayValue2Millisecond(int dayValue) {
    return (dayValue*24*60*60*1000);
  }

  /**
    * Call this function to round off the second and millisecond of time.
    *
    * @param timestamp               The timestamp value to be strip to minutes
    * @return
    */

  public static java.sql.Timestamp stripToMinutes(java.sql.Timestamp timestamp){
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis (timestamp.getTime());
    int day = cal.get (Calendar.DATE);
    int month = cal.get (Calendar.MONTH);
    int year = cal.get (Calendar.YEAR);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int time = cal.get(Calendar.MINUTE);

    cal.clear();
    cal.set (year, month, day, hour, time);
    timestamp = new java.sql.Timestamp (cal.getTimeInMillis());
    return timestamp;
  }


  /**
    * Function to convert a resource bundle into java.util.Map
    *
    * @param bundleName The bundle name to parse
    * @return the key / value pair in java.util.Map format
    */
  public static Map parseResourceBundle(String bundleName){
    Map paraMap = new HashMap();
    ResourceBundle resourceBundle = null;
    try{
      resourceBundle = ResourceBundle.getBundle(bundleName);
      Enumeration resKeys = resourceBundle.getKeys();
      while(resKeys.hasMoreElements()){
        String key = (String)resKeys.nextElement();
        String value = resourceBundle.getString(key);
        if(value==null) {
          value="";
        }
        paraMap.put(key, value);
        }
    }catch(java.util.MissingResourceException e){
      //ignore the exception and return empty map
    }
    return paraMap;
  }

  /**
   *
   * @param stringArray A String  Array
   * @param logic The value is "AND" or "OR"
   * @return the String /
   */
  public static String genericLogicSql(String[] stringArray,String logic){
    String returnTo = null;
    if (!Utility.isEmpty(stringArray)){
      int count =0;
      for(int i=0;i<stringArray.length;i++){
      if(count==0){
        returnTo = stringArray[i];
      }else{
        returnTo = returnTo + " " + logic + " " + stringArray[i];
      }
      count = 1;
      }
    }
    return returnTo;

  }
  
  /**
   * the method's result was used decsion isCurrentDate by startDate and endDate
   * 
   * @param startDate is a specifically Timestamp
   * @param endDate is a specifically Timestamp
   * @return 
   */
  public static boolean isCurrentDataByStartdataAndEnddata(Timestamp startDate,Timestamp endDate) {
    boolean result = false;
    Timestamp currentDate = getCurrentTimestamp();
    if(endDate == null) {
      if(currentDate.compareTo(startDate)>=0) {
        result = true;        
      }
    }else {
      if((currentDate.compareTo(startDate))>=0 && (currentDate.compareTo(endDate))<=0) {
        result = true;
      }
    }
    return result;
  }
  /**
   * delete this repeat character
   * @param repeatStr The String contain the repeat character
   * @param separator The list separator
   * @return noRepeatStr The String has not contain the repeat character
   */
   public static String deleteRepeatStr(String repeatStr,String separator) {
     if(repeatStr==null) {
      return null;
    }
     StringBuffer noRepeatStr = new StringBuffer();
     String[] strElement = repeatStr.split(separator);
     List strlist = new ArrayList();
     for (int i = 0; i < strElement.length; i++) {
       if (!strlist.contains(strElement[i])){
         strlist.add(strElement[i]);
       }
     }
     for (int i = 0; i < strlist.size(); i++) {
       noRepeatStr.append(strlist.get(i));
       if (i != strlist.size() - 1){
         noRepeatStr.append(separator);
       }
     }
     return noRepeatStr.toString();
   } 
  
   /**
    * remove the repeat element in the list
    * @param list
    * @return newList
    */
   public static List removeDuplicate(List list) {
     if (Utility.isEmpty(list)) {
       return null;
     }
     HashSet h = new HashSet(list);
     List newList = new ArrayList();
     newList.addAll(h);
     return newList;
   } 
}