package com.lxitedu.tools.generate;

/*
 * @(#)GenerateBean.java
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


import com.lxitedu.framework.observer.Observer;
import com.lxitedu.tools.generate.subject.Subject;

/**
 * GenerateBean.java
 * 
 * This class is for generation of bean object.
 * 
 * @author Rollo Chan
 * @company DCIVision Limited
 * @creation date 26/06/2003
 * @version $Revision: 1.20 $
 */

public class GenerateBean extends BaseGenerate implements Observer {

  public static final String REVISION = "$Revision: 1.20 $";

  /*
   * public static void main(String[] argv) throws Exception { GenerateBean g =
   * new GenerateBean(); String[] argv1 = { "lx_interviewed", "lxitedu" }; argv
   * = argv1; g.executeGenerate(argv); }
   */

  public GenerateBean() {
  }

  /**
   * @author TangLianfang
   * @param initializeData
   * @explain 得到相的数据生成相应的bean类
   */
  public GenerateBean(Subject subject) {
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

  public static void printConstructor() throws Exception {
    out.println();
    out.println("  public " + className + "() {");
    out.println("    super();");
    out.println("  }");
  }

  public static void printGetSetMethod() throws Exception {
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      if (exceptionFields.get(rsmd.getColumnName(i)) == null) {
        // get method.
        out.println();
        out.println("  public " + getJavaType(rsmd, i) + " get" + getVariableName(rsmd.getColumnName(i), true)
            + "() { ");
        out.println("    return(this." + getVariableName(rsmd.getColumnName(i)) + ");");
        out.println("  }");

        // set method.
        out.println();
        out.println("  public void set" + getVariableName(rsmd.getColumnName(i), true) + "(" + getJavaType(rsmd, i)
            + " " + getVariableName(rsmd.getColumnName(i)) + ") { ");
        out.println("    this." + getVariableName(rsmd.getColumnName(i)) + " = "
            + getVariableName(rsmd.getColumnName(i)) + ";");
        out.println("  }");
      }
    }
  }

  public static void printPrivateMember() throws Exception {
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      System.out.println(":::" + rsmd.getColumnName(i));
      if (exceptionFields.get(rsmd.getColumnName(i)) == null) {
        if (JAVA_INTEGER.equals(getJavaType(rsmd, i))) { // int
          out.println("  private " + getJavaType(rsmd, i) + " " + getVariableName(rsmd.getColumnName(i)) + " = null;");
        } else if (JAVA_DECIMAL.equals(getJavaType(rsmd, i))) { // double
          out.println("  private " + getJavaType(rsmd, i) + " " + getVariableName(rsmd.getColumnName(i)) + " = null;");
        } else if (JAVA_STRING.equals(getJavaType(rsmd, i))) { // String
          out.println("  private " + getJavaType(rsmd, i) + " " + getVariableName(rsmd.getColumnName(i)) + " = null;");
        } else if (JAVA_DATE.equals(getJavaType(rsmd, i))) { // Date
          out.println("  private " + getJavaType(rsmd, i) + " " + getVariableName(rsmd.getColumnName(i)) + " = null;");
        }
      }
    }
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
    out.println("package " + packageName + ".bean;");
    out.println();
    out.println();
    out.println("/**");
    out.println("  " + className + ".java");
    out.println();
    out.println("  This class is the serializable bean reflecting business logic uses.");
    out.println();
    out.println("    @author           " + author);
    out.println("    @company          Lxit Trainning");
    out.println("    @creation date    "
        + (now.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + now.get(Calendar.DAY_OF_MONTH)) : ("" + now
            .get(Calendar.DAY_OF_MONTH))) + "/" + month[now.get(Calendar.MONTH)] + "/" + now.get(Calendar.YEAR));
    out.println("    @version          $Revision: 1.20 $");
    out.println("*/");
    out.println();
    out.println("public class " + className + "  {");
    out.println();
    out.println("  public static final String REVISION = \"$Revision: 1.20 $\";");
    out.println();
  }

  @Override
  protected void executeBody() throws Exception {
    // Open source file.
    openSrcFile();

    // Print the program header.
    printHeader();

    // Initialize database connection.
    openDBConn();

    // Print private member.
    printPrivateMember();

    // Print constructors.
    printConstructor();

    // Print get and set methods.
    printGetSetMethod();

    // Initialize database connection.
    closeDBConn();

    // Print the program footer.
    printFooter();

    // Close source file.
    closeSrcFile();
    // TODO Auto-generated method stub

  }

  @Override
  protected String getSrcFileName() throws Exception {
    srcFileName = srcPath + "/src/" + replaceString(packageName, ".", "/") + "/bean/" + className + ".java";
    return srcFileName;

  }

  @Override
  protected void setupOutFolder() throws Exception {

    String folderName = srcPath + "/src/" + replaceString(packageName, ".", "/");
    File file = new File(folderName);
    if (!file.exists()) {
      file.mkdir();
    }
    Thread.sleep(200);
    file = new File(folderName + "/bean");
    if (!file.exists()) {
      file.mkdir();
    }
  }
}
