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

public class ClassNameActionFreemarker extends BaseGenerateFreemarker implements Observer {

    private static final Log log = LogFactory.getLog(ClassNameActionFreemarker.class);

    /**
     * @author TangLianfang
     * @param initializeData
     * @explain 得到相的数据生成相应的ActionFreemarker类
     */
    public ClassNameActionFreemarker(GeneralSubject subject) {
        subject.attach(this);
    }

    @Override
    public void update(String initializeData[]) {
        try {
            execute(initializeData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    protected String getTemplateName() {
        return "ClassNameAction.ftl";
    }

    @Override
    protected void setSrcFileName() {
        srcFileName = srcPath + "/src/" + replaceString(basePackageName, ".", "/") + "/action/admin/" + className
                + "Action.java";

    }

    @Override
    protected void addExtendOutData(Map root) throws SQLException, Exception {
        // TODO Auto-generated method stub
        super.addExtendOutData(root);
        List columnList = new LinkedList();
        int tt = resultSetMeta.getColumnCount();
        for (int i = 1; i <= tt; i++) {
            String tmp = resultSetMeta.getColumnName(i);

            if (exceptionFields.get(tmp) == null) {
                columnList.add(tmp);
            }
        }
        root.put("columnList", columnList);
    }

    @Override
    protected void setupOutFolder() throws Exception {

        String folderName = srcPath + "/src/" + replaceString(packageName, ".", "/");
        File file = new File(folderName);
        if (!file.exists()) {
            file.mkdir();
        }
        Thread.sleep(200);
        file = new File(folderName + "/action/admin");
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
