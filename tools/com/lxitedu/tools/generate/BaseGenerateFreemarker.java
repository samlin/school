package com.lxitedu.tools.generate;

/*
 * @(#)GenerateEditJSP.java
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * GenerateEditJSP.java
 * 
 * This class is for generation of JSP object.
 * 
 * @author Phoebe Wong
 * @company DCIVision Limited
 * @creation date 15/07/2003
 * @version $Revision: 1.16 $
 */

public abstract class BaseGenerateFreemarker {

    protected static final String SRC_TEMPLATE_PATH = "src/com/lxitedu/tools/generate/template";

    public static final String REVISION = "$Revision: 1.16 $";

    static String author = null;
    static String srcPath = null;
    static String driverStr = null;
    static String dbConnStr = null;
    static String dbUsername = null;
    static String dbPassword = null;

    static String packageName = "";
    static String basePackageName = "com.dcivision";

    static final String JAVA_INTEGER = "Integer";
    static final String JAVA_DECIMAL = "Float";
    static final String JAVA_STRING = "String";
    static final String JAVA_DATE = "Timestamp";

    static Connection conn = null;
    static String sqlStat = null;
    static Statement stat = null;
    static ResultSet rs = null;
    static ResultSetMetaData resultSetMeta = null;

    static PrintStream out = null;
    static String srcFileName = null;
    static String tableName = null;
    static String className = "";
    static String fileName = "";

    static Hashtable exceptionFields = new Hashtable();
    static boolean hasRecordStatus = false;
    private static final Log log = LogFactory.getLog(BaseGenerateFreemarker.class);

    public static void main(String[] argv) throws Exception {
        // Load properties in the "src" directory.
        // execute(argv);
    }

    public void execute(String[] argv) throws Exception {
        initPropertiesVar();

        initCommonFields();

        checkParameter(argv);

        initOutVar(argv);

        setSrcFileName();

        setupOutFolder();

        Map root = getInitRoot();

        addExtendOutData(root);

        Template temp = getFreemarkerConfig().getTemplate(getTemplateName());
        Writer out = new PrintWriter(srcFileName);
        temp.process(root, out);
        out.flush();

        closeDBConn();
    }

    private void checkParameter(String[] argv) throws Exception {
        if (argv.length != 2 && argv.length != 3) {
            System.out.println("Usage: java GenerateEditJSP <TABLE_NAME> <PACKAGE_NAME>");
            throw new Exception(" the argv must be <TABLE_NAME> <PACKAGE_NAME>");
        }
    }

    protected void addExtendOutData(Map root) throws SQLException, Exception {
        List columnList = new LinkedList();
        int tt = getResultSetMeta().getColumnCount();
        for (int i = 1; i <= tt; i++) {
            String tmp = getResultSetMeta().getColumnName(i);

            if (exceptionFields.get(tmp) == null) {
                columnList.add(tmp);
            }
        }

        root.put("columnList", columnList);
    }

    private Map getInitRoot() {
        Map root = new HashMap();
        // Put string ``user'' into the root
        root.put("packageName", packageName);
        root.put("className", className);
        root.put("tableName", tableName);
        return root;
    }

    private Configuration getFreemarkerConfig() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(SRC_TEMPLATE_PATH));

        cfg.setObjectWrapper(new DefaultObjectWrapper());
        return cfg;
    }

    private void initOutVar(String[] argv) {
        // Initialize file and class settings.
        argv[0] = argv[0].toLowerCase();
        String[] tmpAry = splitString(argv[0], "_");
        className = "";
        fileName = "";
        for (int i = 0; i < tmpAry.length; i++) {
            className += Character.toUpperCase(tmpAry[i].charAt(0)) + tmpAry[i].substring(1);
            fileName += Character.toUpperCase(tmpAry[i].charAt(0)) + tmpAry[i].substring(1);
            ;
        }
        System.err.println("class name;" + className);
        System.err.println("file name;" + fileName);
        tableName = argv[0];
        packageName = argv[1];
    }

    private void initCommonFields() {
        // Initialize those common fields which NOT need to generate.
        exceptionFields.put("ID", "Y");
        exceptionFields.put("creatorID", "Y");
        exceptionFields.put("createDate", "Y");
        exceptionFields.put("updaterID", "Y");
        exceptionFields.put("updateDate", "Y");
        exceptionFields.put("recordStatus", "Y");
        exceptionFields.put("updateCount", "Y");
        hasRecordStatus = true;
    }

    private void initPropertiesVar() throws FileNotFoundException, IOException {
        java.util.Properties props = new java.util.Properties();
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
        if (!path.endsWith("/")) {
            path += "/";
        }
        path += "generate.properties";
        java.io.FileInputStream fis = new java.io.FileInputStream(new java.io.File(path));
        props.load(fis);

        author = props.getProperty("author");
        srcPath = props.getProperty("srcPath");
        driverStr = props.getProperty("driverStr");
        dbConnStr = props.getProperty("dbConnStr");
        dbUsername = props.getProperty("dbUsername");
        dbPassword = props.getProperty("dbPassword");
    }

    protected abstract void setSrcFileName();

    public static Connection getConnection() throws Exception {
        if (conn == null) {
            Class.forName(driverStr);
            conn = DriverManager.getConnection(dbConnStr, dbUsername, dbPassword);
        }
        return conn;
    }

    public static ResultSetMetaData getResultSetMeta() throws Exception {

        if (resultSetMeta == null) {
            sqlStat = "SELECT * FROM " + tableName.toUpperCase();
            stat = getConnection().createStatement();
            stat.setMaxRows(1);
            rs = stat.executeQuery(sqlStat);
            resultSetMeta = rs.getMetaData();

        }
        return resultSetMeta;
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

    public static String getVariableName(String dbColName) {
        return (getVariableName(dbColName, false));
    }

    public static String getVariableName(String dbColName, boolean capInitFlag) {
        String result = "";
        String[] tmpAry = splitString(dbColName.toLowerCase(), "_");
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

    /**
     * Convert variable name in ApplicationResources format e.g:
     * user.label.function_code
     * 
     * @param dbColName
     *          The String to convert
     * @return sResult The converted string
     */
    public static String getARName(String dbColName) {
        String sResult = packageName + ".label.";
        String[] tmpAry = splitString(dbColName.toLowerCase(), "_");
        for (int i = 0; i < tmpAry.length; i++) {
            sResult += tmpAry[i];
            if (i < (tmpAry.length - 1)) {
                sResult += "_";
            }
        }
        return (sResult);
    }

    /**
     * To check the variable type
     * 
     * @param inRSMD
     *          The column name of the variable to be check
     * @param idx
     *          The column id of the variable to be check
     * @return The variable type
     */
    public static String getJavaType(ResultSetMetaData inRSMD, int idx) throws Exception {
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
            log.error(e, e);
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
                // If you got exception here, please check your JDK version. JDK
                // 1.4 is needed for this method.
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
                log.error(e, e);
            }
        }
        return result;
    }

    protected abstract String getTemplateName();

    protected abstract void setupOutFolder() throws Exception;
}
