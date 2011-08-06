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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Hashtable;

import com.lxitedu.framework.observer.Observer;

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

public abstract class BaseGenerate implements Observer {

    public static final String REVISION = "$Revision: 1.20 $";

    static String author = null;
    static String srcPath = null;
    static String driverStr = null;
    static String dbConnStr = null;
    static String dbUsername = null;
    static String dbPassword = null;

    static String packageName = "";
    static String rootPackageName = "com.lxit.";

    static final String JAVA_INTEGER = "Integer";
    static final String JAVA_DECIMAL = "Float";
    static final String JAVA_STRING = "String";
    static final String JAVA_DATE = "Timestamp";

    static Connection conn = null;
    static String sqlStat = null;
    static Statement stat = null;
    static ResultSet rs = null;
    protected static ResultSetMetaData rsmd = null;

    static PrintStream out = null;
    static String srcFileName = null;
    static String tableName = null;
    static String className = "";

    static Hashtable exceptionFields = new Hashtable();
    static boolean hasRecordStatus = false;

    public static void main(String[] argv) throws Exception {

        // init(argv);

        // Open source file.
        openSrcFile();

        // Print the program header.

        // Initialize database connection.
        openDBConn();

        // Print private member.

        // Print constructors.

        // Print get and set methods.

        // Initialize database connection.
        closeDBConn();

        // Print the program footer.
        printFooter();

        // Close source file.
        closeSrcFile();
    }

    protected void init(String[] argv) throws Exception {
        initPropertiesVar();

        initVar(argv);

        initCommonFields();

        srcFileName = getSrcFileName();

        System.err.println("this is before setupOutFolder");
        setupOutFolder();
    }

    protected static void initCommonFields() {
        // Initialize those common fields which NOT need to generate.
        exceptionFields.put("ID", "Y");
        exceptionFields.put("creatorID", "Y");
        exceptionFields.put("createDate", "Y");
        exceptionFields.put("updaterID", "Y");
        exceptionFields.put("updateDate", "Y");
        exceptionFields.put("recordStatus", "Y");
        exceptionFields.put("updateCount", "Y");
        exceptionFields.put("modifyDate", "Y");
        hasRecordStatus = true;
    }

    protected static void initPropertiesVar() throws FileNotFoundException, IOException {
        // Load properties in the "src" directory.
        java.util.Properties props = new java.util.Properties();
        String path = new GenerateBean().getClass().getProtectionDomain().getCodeSource().getLocation().toString()
                .substring(6);
        if (!path.endsWith("/")) {
            path += "/";
        }
        path += "jdbc.properties";
        java.io.FileInputStream fis = new java.io.FileInputStream(new java.io.File(path));
        props.load(fis);
        author = props.getProperty("author");
        srcPath = props.getProperty("srcPath");
        driverStr = props.getProperty("jdbc.driver");
        dbConnStr = props.getProperty("jdbc.url");
        dbUsername = props.getProperty("jdbc.username");
        dbPassword = props.getProperty("jdbc.password");
    }

    protected static void initVar(String[] argv) {
        // Check the usage.
        if (argv.length != 2 && argv.length != 3) {
            System.out.println("Usage: java GenerateBean <TABLE_NAME> <PACKAGE_NAME>");
            return;
        }
        className = "";
        // Initialize file and class settings.
        argv[0] = argv[0].toLowerCase();
        String[] tmpAry = splitString(argv[0], "_");
        for (int i = 0; i < tmpAry.length; i++) {
            className += Character.toUpperCase(tmpAry[i].charAt(0)) + tmpAry[i].substring(1);
        }
        tableName = argv[0];
        packageName = rootPackageName + argv[1];
    }

    public static void openDBConn() throws Exception {
        Class.forName(driverStr);
        conn = DriverManager.getConnection(dbConnStr, dbUsername, dbPassword);

        sqlStat = "SELECT * FROM " + tableName.toUpperCase();
        stat = conn.createStatement();
        stat.setMaxRows(1);
        rs = stat.executeQuery(sqlStat);
        rsmd = rs.getMetaData();
    }

    public static void closeDBConn() throws Exception {
        try {
            rs.close();
        } catch (Exception ignore) {
        } finally {
            rs = null;
        }
        try {
            stat.close();
        } catch (Exception ignore) {
        } finally {
            stat = null;
        }
        try {
            conn.close();
        } catch (Exception ignore) {
        } finally {
            conn = null;
        }
    }

    public static void printFooter() throws Exception {
        out.println("}");
    }

    public static void openSrcFile() throws Exception {
        try {
            out = new PrintStream(new FileOutputStream(srcFileName, false), true);
        } catch (Exception e) {
            e.printStackTrace();
            out = System.out;
        }
    }

    public static void closeSrcFile() throws Exception {
        try {
            out.close();
        } catch (Exception ignore) {
        } finally {
            out = null;
        }
    }

    public static String getVariableName(String dbColName) {
        return (getVariableName(dbColName, false));
    }

    public static String getVariableName(String dbColName, boolean capInitFlag) {
        String result = "";
        String[] tmpAry = splitString(dbColName, "_");
        for (int i = 0; i < tmpAry.length; i++) {
            String tmp = Character.toUpperCase(tmpAry[i].charAt(0)) + tmpAry[i].substring(1);
            if ("id".equals(tmp.toLowerCase())) {
                result += "ID";
            } else {
                result += tmp;
            }
        }
        if (capInitFlag) {
            return (result);
        } else {
            if (result.toLowerCase().equals("id") && !capInitFlag) {
                return ("id");
            } else {
                return (Character.toLowerCase(result.charAt(0)) + result.substring(1));
            }
        }
    }

    public static String getJavaType(ResultSetMetaData inRSMD, int idx) throws Exception {
        System.out.println(inRSMD.getColumnName(idx) + ":" + inRSMD.getColumnTypeName(idx) + ":"
                + inRSMD.getColumnType(idx) + ":" + inRSMD.getPrecision(idx));
        if ((inRSMD.getColumnType(idx) == Types.BIGINT) || (inRSMD.getColumnType(idx) == Types.INTEGER)) { // int
            return (JAVA_INTEGER);
        } else if ((inRSMD.getColumnType(idx) == Types.FLOAT) || (inRSMD.getColumnType(idx) == Types.DECIMAL)
                || (inRSMD.getColumnType(idx) == Types.DOUBLE)) { // double
            return (JAVA_DECIMAL);
        } else if ((inRSMD.getColumnType(idx) == Types.VARCHAR) || (inRSMD.getColumnType(idx) == Types.CHAR)
                || (inRSMD.getColumnType(idx) == Types.LONGVARCHAR)) { // String
            return (JAVA_STRING);
        } else if ((inRSMD.getColumnType(idx) == Types.DATE) || (inRSMD.getColumnType(idx) == Types.TIMESTAMP)) { // Date
            return (JAVA_DATE);
        } else {
            return ("<UNKNOWN>");
        }
    }

    public static String getJavaType(ResultSetMetaData inRSMD, int idx, boolean initCapFlag) throws Exception {
        System.out.println(inRSMD.getColumnName(idx) + ":" + inRSMD.getColumnTypeName(idx) + ":"
                + inRSMD.getColumnType(idx) + ":" + inRSMD.getPrecision(idx));
        if ((inRSMD.getColumnType(idx) == Types.BIGINT) || (inRSMD.getColumnType(idx) == Types.INTEGER)) { // int
            return (initCapFlag ? capStrInit(JAVA_INTEGER) : JAVA_INTEGER);
        } else if ((inRSMD.getColumnType(idx) == Types.FLOAT) || (inRSMD.getColumnType(idx) == Types.DECIMAL)
                || (inRSMD.getColumnType(idx) == Types.DOUBLE)) { // double
            return (initCapFlag ? capStrInit(JAVA_DECIMAL) : JAVA_DECIMAL);
        } else if ((inRSMD.getColumnType(idx) == Types.VARCHAR) || (inRSMD.getColumnType(idx) == Types.CHAR)
                || (inRSMD.getColumnType(idx) == Types.LONGVARCHAR)) { // String
            return (initCapFlag ? capStrInit(JAVA_STRING) : JAVA_STRING);
        } else if ((inRSMD.getColumnType(idx) == Types.DATE) || (inRSMD.getColumnType(idx) == Types.TIMESTAMP)) { // Date
            return (initCapFlag ? capStrInit(JAVA_DATE) : JAVA_DATE);
        } else {
            return ("<UNKNOWN>");
        }
    }

    public static String capStrInit(String str) {
        return (Character.toUpperCase(str.charAt(0)) + str.substring(1));
    }

    /**
     * splitString
     * 
     * Returns string array which after spliting the input string.
     * 
     * @param src
     *          String which going to be splitted.
     * @param div
     *          Delimitator for split.
     * @return String array after split.
     */
    public static String[] splitString(String src, String div) {
        String[] result = null;
        java.util.Vector temp = new java.util.Vector();
        int idx1 = 0;
        int idx2 = 0;

        if (src == null || src.length() == 0 || div == null || div.length() == 0) {
            return (null);
        }

        try {
            while (idx2 >= 0 && idx1 < src.length()) {
                idx2 = src.indexOf(div, idx1);
                if (idx2 >= 0) {
                    temp.addElement(src.substring(idx1, idx2));
                } else {
                    temp.addElement(src.substring(idx1));
                }
                idx1 = idx2 + div.length();
            }

            result = new String[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                result[i] = (String) temp.elementAt(i);
            }

            return (result);
        } catch (Exception e) {
            e.printStackTrace();
            return (null);
        }
    }

    /**
     * splitString
     * 
     * Returns string array which after spliting the input string using space as
     * delimitator.
     * 
     * @param src
     *          String which going to be splitted using space as delimitator.
     * @return String array after split.
     */
    public static String[] splitString(String src) {
        return (splitString(src, " "));
    }

    /**
     * replaceString
     * 
     * Returns string after replacing the original string to the target string, no
     * matter the ori string wrapped by space or charater.
     * 
     * @param src
     *          Source string which going to be replaced
     * @param ori
     *          Original string in the source string
     * @param tar
     *          Target string which replaces the original string
     * @return String after replacement taken place.
     */
    public static String replaceString(String src, String ori, String tar) {
        String result = null;

        if (src != null && ori != null && tar != null) {
            try {
                StringBuffer source = new StringBuffer(src);
                int appearedIndex = 0;
                int oriLength = ori.length();
                int tarLength = tar.length();
                int byPassingIndex = 0;
                // If you got exception here, please check your JDK version. JDK 1.4 is
                // needed for this method.
                while (source.indexOf(ori, byPassingIndex) != -1) {
                    appearedIndex = source.indexOf(ori, byPassingIndex);
                    if (ori.equals(source.substring(appearedIndex, appearedIndex + oriLength))) {
                        source = source.replace(appearedIndex, appearedIndex + oriLength, tar);
                        byPassingIndex = appearedIndex + tarLength;
                    } else {
                        byPassingIndex = appearedIndex;
                    }
                }
                result = source.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void executeGenerate(String[] argv) throws Exception {
        init(argv);

        executeBody();

    }

    protected abstract void executeBody() throws Exception;

    protected abstract String getSrcFileName() throws Exception;

    protected abstract void setupOutFolder() throws Exception;

    @Override
    public void update(String[] initializeData) throws Exception {
        executeGenerate(initializeData);

    }
}
