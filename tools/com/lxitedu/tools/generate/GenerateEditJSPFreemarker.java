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
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lxitedu.tools.generate.observer.Observer;
import com.lxitedu.tools.generate.subject.GeneralSubject;

/**
 * GenerateEditJSP.java
 * 
 * This class is for generation of JSP object.
 * 
 * @author Samlin Zhang
 * @company Lxit Limited
 * @creation date 15/07/2003
 * @version $Revision: 1.16 $
 */

public class GenerateEditJSPFreemarker extends BaseGenerateFreemarker implements Observer {

    public static final String REVISION = "$Revision: 1.16 $";

    private static final Log log = LogFactory.getLog(GenerateEditJSPFreemarker.class);

    /**
     * @author TangLianfang
     * @param initializeData
     * @explain 得到相的数据生成相应的JSPFreemarker类
     */
    public GenerateEditJSPFreemarker(GeneralSubject subject) {
        subject.attach(this);
    }

    @Override
    public void update(String initializeData[]) {
        try {
            execute(initializeData);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    protected String getTemplateName() {
        return "EditClassNameJsp.ftl";
    }

    @Override
    protected void setSrcFileName() {
        srcFileName = srcPath + "/web/jsp/" + packageName + "/Edit" + fileName + ".jsp";
    }

    @Override
    protected void addExtendOutData(Map root) throws SQLException, Exception {
        // TODO Auto-generated method stub
        super.addExtendOutData(root);
        List columnList = new LinkedList();
        int tt = resultSetMeta.getColumnCount();
        LXColumn column;
        for (int i = 1; i <= tt; i++) {
            column = new LXColumn();
            column.setColumnName(resultSetMeta.getColumnName(i));
            System.out.println("columnName:" + resultSetMeta.getColumnName(i));
            System.out.println("isNullable:" + resultSetMeta.isNullable(i));
            column.setIsNullable(resultSetMeta.isNullable(i));
            column.setColumnDisplaySize(resultSetMeta.getColumnDisplaySize(i));
            System.out.println("columnDisplaySize:" + resultSetMeta.getColumnDisplaySize(i));

            if (exceptionFields.get(column.getColumnName()) == null) {
                columnList.add(column);
            }
        }
        root.put("columnList", columnList);
    }

    @Override
    protected void setupOutFolder() throws Exception {

        String folderName = srcPath + "/web/jsp/" + replaceString(packageName, ".", "/");
        File file = new File(folderName);
        if (!file.exists()) {
            file.mkdir();
        }
        // Thread.sleep(200);
        // file = new File(folderName + "/dao");
        // if (!file.exists()) {
        // file.mkdir();
        // }
    }
}
