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

public class ClassNameDaoFreemarker extends BaseGenerateFreemarker implements Observer {

    public static final String REVISION = "$Revision: 1.16 $";

    private static final Log log = LogFactory.getLog(ClassNameDaoFreemarker.class);

    /**
     * @author TangLianfang
     * @param initializeData
     * @explain 得到相的数据生成相应的ActionFreemarker类
     */
    public ClassNameDaoFreemarker(GeneralSubject subject) {
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
        return "ClassNameDao.ftl";
    }

    @Override
    protected void setSrcFileName() {
        srcFileName = srcPath + "/src/" + replaceString(basePackageName, ".", "/") + "/dao/" + className + "Dao.java";

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
        file = new File(folderName + "/dao");
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
