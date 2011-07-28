package com.lxitedu.tools.generate;

/*
 * @(#)GenerateDAO.java
 *
 * Copyright (c) 2003 DCIVision Ltd
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DCIVision
 * Ltd ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with DCIVision Ltd.
 */
import java.io.File;
import java.util.Calendar;


import com.lxitedu.tools.generate.subject.Subject;
import com.mysql.jdbc.Connection;

/**
 * GenerateDAO.java
 * 
 * Code generator for generating DAObject java files.
 * 
 * @author Samlin Chan
 * @company DCIVision Limited
 * @creation date 05/07/2003
 * @version $Revision: 1.38 $
 */

public class GenerateDAO extends BaseGenerate {

  public static final String REVISION = "$Revision: 1.38 $";


  /**
   * @author TangLianfang
   * @param initializeData
   * @explain 得到相的数据生成相应的dao类
   */
  public GenerateDAO(Subject subject) {
    subject.attach(this);
  }

  @Override
  public void update(String initializeData[]) {
    try {
      executeGenerate(initializeData);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void printDeleteObject() throws Exception {
    out
        .println("  protected synchronized AbstractBaseObject delete(AbstractBaseObject obj) throws ApplicationException {");
    out.println("    PreparedStatement preStat = null;");
    out.println("    StringBuffer sqlStat = new StringBuffer();");
    out.println("    " + className + " tmp" + className + " = (" + className + ")((" + className + ")obj).clone();");
    out.println();
    out.println("    synchronized(dbConn) {");
    out.println("      try {");
    out.println("        int updateCnt = 0;");
    out.println("        sqlStat.append(\"DELETE \");");
    out.println("        sqlStat.append(\"FROM   " + tableName.toUpperCase() + " \");");
    out.println("        sqlStat.append(\"WHERE  ID=? AND UPDATECOUNT=? \");");
    out
        .println("        preStat = dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);");
    out.println("        setPrepareStatement(preStat, 1, tmp" + className + ".getID());");
    out.println("        setPrepareStatement(preStat, 2, tmp" + className + ".getUpdateCount());");
    out.println("        updateCnt = preStat.executeUpdate();");
    out.println("        if (updateCnt == 0) {");
    out.println("          throw new ApplicationException(ErrorConstant.DB_CONCURRENT_ERROR);");
    out.println("        } else {");
    out.println("          return(tmp" + className + ");");
    out.println("        }");
    out.println("      } catch (ApplicationException appEx) {");
    out.println("        throw appEx;");
    out.println("      } catch (SQLException sqle) {");
    out.println("        log.error(sqle, sqle);");
    out.println("        throw new ApplicationException(ErrorConstant.DB_GENERAL_ERROR, sqle, sqle.toString());");
    out.println("      } catch (Exception e) {");
    out.println("        log.error(e, e);");
    out.println("        throw new ApplicationException(ErrorConstant.DB_DELETE_ERROR, e);");
    out.println("      } finally {");
    out.println("        try { preStat.close(); } catch (Exception ignore) {} finally { preStat = null; }");
    out.println("      }");
    out.println("    }");
    out.println("  }");
    out.println();
  }

  public static void printUpdateObject() throws Exception {
    out
        .println("  protected synchronized AbstractBaseObject update(AbstractBaseObject obj) throws ApplicationException {");
    out.println("    PreparedStatement preStat = null;");
    out.println("    StringBuffer sqlStat = new StringBuffer();");
    out.println("    " + className + " tmp" + className + " = (" + className + ")((" + className + ")obj).clone();");
    out.println();
    out.println("    synchronized(dbConn) {");
    out.println("      try {");
    out.println("        int updateCnt = 0;");
    out.println("        Timestamp currTime = Utility.getCurrentTimestamp();");
    out.println("        sqlStat.append(\"UPDATE " + tableName.toUpperCase() + " \");");
    out.print("        sqlStat.append(\"SET  ");
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      if (!rsmd.getColumnName(i).toUpperCase().equals("CREATEDATE")
          && !rsmd.getColumnName(i).toUpperCase().equals("CREATORID")
          && !rsmd.getColumnName(i).toUpperCase().equals("ID")
          && !rsmd.getColumnName(i).toUpperCase().equals("RECORDSTATUS")) {
        out.print(rsmd.getColumnName(i) + "=?");
        if (i != rsmd.getColumnCount()) {
          out.print(", ");
        }
      }
    }
    out.println(" \");");
    out.println("        sqlStat.append(\"WHERE  ID=? AND UPDATECOUNT=? \");");
    out
        .println("        preStat = dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);");
    int idx = 1;
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      if (exceptionFields.get(rsmd.getColumnName(i)) == null) {
        out.println("        setPrepareStatement(preStat, " + idx + ", tmp" + className + ".get"
            + getVariableName(rsmd.getColumnName(i), true) + "());");
        idx++;
      }
    }
    out.println("        setPrepareStatement(preStat, " + (idx++) + ", new Integer(tmp" + className
        + ".getUpdateCount().intValue() + 1));");
    out.println("        setPrepareStatement(preStat, " + (idx++) + ", sessionContainer.getUserRecordID());");
    out.println("        setPrepareStatement(preStat, " + (idx++) + ", currTime);");
    out.println("        setPrepareStatement(preStat, " + (idx++) + ", tmp" + className + ".getID());");
    out.println("        setPrepareStatement(preStat, " + (idx++) + ", tmp" + className + ".getUpdateCount());");
    out.println("        updateCnt = preStat.executeUpdate();");
    out.println("        if (updateCnt == 0) {");
    out.println("          throw new ApplicationException(ErrorConstant.DB_CONCURRENT_ERROR);");
    out.println("        } else {");
    out.println("          tmp" + className + ".setUpdaterID(sessionContainer.getUserRecordID());");
    out.println("          tmp" + className + ".setUpdateDate(currTime);");
    out.println("          tmp" + className + ".setUpdateCount(new Integer(tmp" + className
        + ".getUpdateCount().intValue() + 1));");
    out.println("          tmp" + className + ".setCreatorName(UserInfoFactory.getUserFullName(tmp" + className
        + ".getCreatorID()));");
    out.println("          tmp" + className + ".setUpdaterName(UserInfoFactory.getUserFullName(tmp" + className
        + ".getUpdaterID()));");
    out.println("          return(tmp" + className + ");");
    out.println("        }");
    out.println("      } catch (ApplicationException appEx) {");
    out.println("        throw appEx;");
    out.println("      } catch (SQLException sqle) {");
    out.println("        log.error(sqle, sqle);");
    out.println("        throw new ApplicationException(ErrorConstant.DB_GENERAL_ERROR, sqle, sqle.toString());");
    out.println("      } catch (Exception e) {");
    out.println("        log.error(e, e);");
    out.println("        throw new ApplicationException(ErrorConstant.DB_UPDATE_ERROR, e);");
    out.println("      } finally {");
    out.println("        try { preStat.close(); } catch (Exception ignore) {} finally { preStat = null; }");
    out.println("      }");
    out.println("    }");
    out.println("  }");
    out.println();
  }

  public static void printCreateObject() throws Exception {
    out
        .println("  protected synchronized "+className+" insert("+className+" obj)   {");
    out.println("    PreparedStatement preStat = null;");
    out.println("    StringBuffer sqlStat = new StringBuffer();");
    out.println();
    out.println("    synchronized(dbConn) {");
    out.println("      try {");
    out.println("        " + JAVA_INTEGER + " nextID = getNextPrimaryID();");
    out.println("        sqlStat.append(\"INSERT \");");
    out.print("        sqlStat.append(\"INTO   " + tableName.toUpperCase() + "(");
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      out.print(rsmd.getColumnName(i));
      if (i != rsmd.getColumnCount()) {
        out.print(", ");
      }
    }
    out.println(") \");");
    out.print("        sqlStat.append(\"VALUES (");
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      out.print("?");
      if (i != rsmd.getColumnCount()) {
        out.print(", ");
      }
    }
    out.println(") \");");
    out
        .println("        preStat = dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);");
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      if (i == 1) {
        out.println("        setPrepareStatement(preStat, 1, nextID);");
      } else if (exceptionFields.get(rsmd.getColumnName(i).toUpperCase()) == null) {
        out.println("        setPrepareStatement(preStat, " + i + ",obj" + ".get"
            + getVariableName(rsmd.getColumnName(i), true) + "());");
      }
    }
    out.println("        preStat.executeUpdate();");
    
    out.println("        return obj;");
    out.println("      } catch (Exception Ex) {");
    out.println("        Ex.printStackTrace();");
    out.println("      } finally {");
    out.println("        try { preStat.close(); } catch (Exception ignore) {} finally { preStat = null; }");
    out.println("      }");
    out.println("    }");
    out.println(" return obj;");
    out.println("  }");
    out.println();
  }

  public static void printGetObjectByID() throws Exception {
    out.println("  protected synchronized AbstractBaseObject getByID(" + JAVA_INTEGER
        + " id) throws ApplicationException {");
    out.println("    PreparedStatement preStat = null;");
    out.println("    ResultSet rs = null;");
    out.println("    StringBuffer sqlStat = new StringBuffer();");
    out.println();
    out.println("    synchronized(dbConn) {");
    out.println("      try {");
    out.print("        sqlStat.append(\"SELECT ");
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      out.print("A." + rsmd.getColumnName(i));
      if (i != rsmd.getColumnCount()) {
        out.print(", ");
      }
    }
    out.println(" \");");
    out.println("        sqlStat.append(\"FROM   " + tableName.toUpperCase() + " A \");");
    out.println("        sqlStat.append(\"WHERE  A." + getIDFieldName() + " = ? AND A.recordStatus = ? \");");
    out
        .println("        preStat = dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);");
    out.println("        this.setPrepareStatement(preStat, 1, id);");
    out.println("        this.setPrepareStatement(preStat, 2, GlobalConstant.RECORD_STATUS_ACTIVE);");
    out.println("        rs = preStat.executeQuery();");
    out.println("        if (rs.next()) {");
    out.println("          " + className + " tmp" + className + " = new " + className + "();");
    System.out.println("**********className is **********:" + className);
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      out.println("          tmp" + className + ".set" + getVariableName(rsmd.getColumnName(i), true) + "(getResultSet"
          + getJavaType(rsmd, i, true) + "(rs, \"" + rsmd.getColumnName(i) + "\"));");
    }
    out.println("          tmp" + className + ".setCreatorName(UserInfoFactory.getUserFullName(tmp" + className
        + ".getCreatorID()));");
    out.println("          tmp" + className + ".setUpdaterName(UserInfoFactory.getUserFullName(tmp" + className
        + ".getUpdaterID()));");
    out.println("          return(tmp" + className + ");");
    out.println("        } else {");
    out.println("          throw new ApplicationException(ErrorConstant.DB_RECORD_NOT_FOUND_ERROR);");
    out.println("        }");
    out.println("      } catch (ApplicationException appEx) {");
    out.println("        throw appEx;");
    out.println("      } catch (SQLException sqle) {");
    out.println("        log.error(sqle, sqle);");
    out.println("        throw new ApplicationException(ErrorConstant.DB_GENERAL_ERROR, sqle, sqle.toString());");
    out.println("      } catch (Exception e) {");
    out.println("        log.error(e, e);");
    out.println("        throw new ApplicationException(ErrorConstant.DB_SELECT_ERROR, e);");
    out.println("      } finally {");
    out.println("        try { rs.close(); } catch (Exception ignore) {} finally { rs = null; }");
    out.println("        try { preStat.close(); } catch (Exception ignore) {} finally { preStat = null; }");
    out.println("      }");
    out.println("    }");
    out.println("  }");
    out.println();
  }

  
  
  public static String getIDFieldName() throws Exception {
    return ("ID");
  }

  public static void printHeader() throws Exception {
    // Print the program header.
    Calendar now = Calendar.getInstance();
    String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
    out.println("/*");
    out.println(" * @(#)" + className + ".java");
    out.println(" *");
    out.println(" * Copyright (c) 2003 DCIVision Ltd");
    out.println(" * All rights reserved.");
    out.println(" *");
    out.println(" * This software is the confidential and proprietary information of DCIVision");
    out.println(" * Ltd (\"Confidential Information\").  You shall not disclose such Confidential");
    out.println(" * Information and shall use it only in accordance with the terms of the license");
    out.println(" * agreement you entered into with DCIVision Ltd.");
    out.println(" */");
    out.println("package " + packageName + ".dao;");
    out.println();
    out.println("import java.sql.*;");
    out.println("import com.lxitedu.framework.dao.SuperDAO;");
    out.println("import java.util.*;");
    out.println("import " + packageName + ".bean.*;");
    out.println();
    out.println("/**");
    out.println("  " + className + "DAObject.java");
    out.println();
    out.println("  This class is the data access bean for table \"" + tableName.toUpperCase() + "\".");
    out.println();
    out.println("  @author      " + author);
    out.println("  @company     DCIVision Limited");
    out.println("  @creation date   "
        + (now.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + now.get(Calendar.DAY_OF_MONTH)) : ("" + now
            .get(Calendar.DAY_OF_MONTH))) + "/" + month[now.get(Calendar.MONTH)] + "/" + now.get(Calendar.YEAR));
    out.println("  @version     $Revision: 1.38 $");
    out.println("*/");
    out.println();

    out.println("public class " + className + "DAObject  extends SuperDAO {");
    out.println();
    out.println("  public static final String REVISION = \"$Revision: 1.38 $\";");
    out.println();
    out.println("  public static final String TABLE_NAME = \"" + tableName.toUpperCase() + "\";");
    out.println();
    out.println("  private Connection dbConn;");
    out.println();
    out.println("  public " + className + "DAObject() {");
    out.println("    ;");
    out.println("  }");
    out.println();
  }

  @Override
  protected void executeBody() throws Exception {
    // TODO Auto-generated method stub
    // Open source file.
    openSrcFile();

    // Initialize database connection.
    openDBConn();

    // Print the program header.
    printHeader();

    // Print Select function.
//    printGetObjectByID();

    // Print Get List function.

    // Print Get List With No Argument function.

    // Print Validation functions.

    // Print Insert function.
    printCreateObject();

    // Print Update function.
//    printUpdateObject();

    // Print Update function.
//    printDeleteObject();

    // Print Prepare Audit Trail function

    // Print Warning function.
//    printWarning();

    // Initialize database connection.
//    closeDBConn();

    // Print the program footer.
    printFooter();

    // Close source file.
    closeSrcFile();
  }

  @Override
  protected String getSrcFileName() throws Exception {
    srcFileName = srcPath + "/src/" + replaceString(packageName, ".", "/") + "/dao/" + className + "DAObject.java";
    return srcFileName;
  }

  protected void setupOutFolder() throws Exception {

    String folderName = srcPath + "/src/" + replaceString(packageName, ".", "/");
    File file = new File(folderName);
    if (!file.exists()) {
      file.mkdir();
    }
    Thread.sleep(200);
    file = new File(folderName + "/dao");
    if (!file.exists()) {
      file.mkdir();
    }
  }
}
