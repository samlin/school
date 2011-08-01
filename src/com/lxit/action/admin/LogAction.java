package com.lxit.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.Log;
import com.lxit.service.LogService;

@ParentPackage("admin")
public class LogAction extends BaseAdminAction {

    private static final long serialVersionUID = 8784555891643520648L;

    private Log log;

    @Resource
    private LogService logService;

    // 列表
    public String list() {
        pager = logService.findByPager(pager);
        return LIST;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

}