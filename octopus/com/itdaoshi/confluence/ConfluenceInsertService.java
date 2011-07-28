package com.itdaoshi.confluence;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.itdaoshi.dbsource.DBTools;
import com.itdaoshi.dbsource.SourceUser;
import com.lxitedu.bean.Student;
import com.lxitedu.dao.DBManager;
import com.lxitedu.framework.tools.PinyinTools;

public class ConfluenceInsertService {
  public static void insertUser() throws UnsupportedEncodingException {

    // Create a method instance.
    // String url = "http://192.168.1.246:8080/confluence-3.0.0_01/Admin";

    // new NameValuePair("condition", new String(condition.getBytes(),"8859_1"))
    // content = new NameValuePair("fullname", new String("’≈”—¡º".getBytes(),
    // "8859_1"));

    List<SourceUser> list = DBTools.getFinalUserInfo();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      SourceUser next = (SourceUser) iterator.next();
      postNewUser(next);
    }

    // GetMethod method = new GetMethod(url);

    // Provide custom retry handler is necessary
    // method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
    // new DefaultHttpMethodRetryHandler(3, false));
  }

  public static void postNewUser(SourceUser next) {
    String url = "http://192.168.1.246:88/wiki/Admin";
    PostMethod method = new PostMethod(url);
    HttpClient client = new HttpClient();
    NameValuePair[] contentreturn = new NameValuePair[5];
    SourceUser sourceUser = next;
    NameValuePair loginName = new NameValuePair("loginName", sourceUser.getLoginName());
    NameValuePair fullName = new NameValuePair("fullName", sourceUser.getStudentName());
    NameValuePair password = new NameValuePair("password", sourceUser.getPassword());
    NameValuePair email = new NameValuePair("email", sourceUser.getEmail());
    NameValuePair groupName = new NameValuePair("groupName", sourceUser.getClassName());
    contentreturn[0] = loginName;
    contentreturn[1] = fullName;
    contentreturn[2] = password;
    contentreturn[3] = email;
    contentreturn[4] = groupName;
    method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    method.setQueryString(contentreturn);
    try {
      try {
        int statusCode = client.executeMethod(method);
        if (statusCode != HttpStatus.SC_OK) {
          System.err.println("Method failed: " + method.getStatusLine());
        }
        byte[] responseBody = method.getResponseBody();
        System.out.println(new String(responseBody));
      } catch (HttpException e) {
        System.err.println("Fatal protocol violation: " + e.getMessage());
        e.printStackTrace();
      } catch (IOException e) {
        System.err.println("Fatal transport error: " + e.getMessage());
        e.printStackTrace();
      } finally {
        method.releaseConnection();
      }
      Thread.sleep(200);
      System.out.println("Ececute" + sourceUser.getStudentName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void postNewUserFromStudent(Student student) {
    String url = "http://192.168.1.246:88/wiki/Admin";
    PostMethod method = new PostMethod(url);
    HttpClient client = new HttpClient();
    NameValuePair[] contentreturn = new NameValuePair[5];
    Student sourceUser = student;
    NameValuePair loginName = new NameValuePair("loginName", PinyinTools.getLoginNameFromStudent(sourceUser));
    NameValuePair fullName = new NameValuePair("fullName", sourceUser.getName());
    NameValuePair password = new NameValuePair("password", PinyinTools.getLoginNameFromStudent(sourceUser));
    NameValuePair email = new NameValuePair("email", PinyinTools.getLoginNameFromStudent(sourceUser) + "gmail.com");
    NameValuePair groupName = new NameValuePair("groupName", sourceUser.getClassId());
    contentreturn[0] = loginName;
    contentreturn[1] = fullName;
    contentreturn[2] = password;
    contentreturn[3] = email;
    contentreturn[4] = groupName;
    method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    method.setQueryString(contentreturn);
    try {
      try {
        int statusCode = client.executeMethod(method);
        if (statusCode != HttpStatus.SC_OK) {
          System.err.println("Method failed: " + method.getStatusLine());
        }
        byte[] responseBody = method.getResponseBody();
        System.out.println(new String(responseBody));
      } catch (HttpException e) {
        System.err.println("Fatal protocol violation: " + e.getMessage());
        e.printStackTrace();
      } catch (IOException e) {
        System.err.println("Fatal transport error: " + e.getMessage());
        e.printStackTrace();
      } finally {
        method.releaseConnection();
      }
      Thread.sleep(200);
      System.out.println("Ececute" + sourceUser.getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void createUsersFromClassId(String classId) {
    List<Student> studentList = new LinkedList<Student>();
    studentList = DBManager.getStudentListFromClassId(classId);
    for (Student student : studentList) {
      postNewUserFromStudent(student);
    }

  }
}
