package com.lxit.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.Homework;
import com.lxit.service.HomeworkService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class HomeworkAction extends BaseAdminAction {

    private static final long serialVersionUID = -5383463207248344967L;

    private Homework entity;

    @Resource
    private HomeworkService service;


    // 查看
    public String view() {
         entity = service.load(id);
        return VIEW;
    }

    // 列表
    public String list() {
        pager = service.findByPager(pager);
        return LIST;
    }

    // 删除
    public String delete() {

        service.delete(id);
        return ajaxJsonSuccessMessage("删除成功！");
    }

    // 添加
    public String add() {
        return INPUT;
    }

    // 编辑
    public String edit() {
        setEntity(service.load(id));
        return INPUT;
    }

    @InputConfig(resultName = "error")
    public String save() {

        redirectionUrl = "homework!list.action";
        return SUCCESS;
    }

    @InputConfig(resultName = "error")
    public String update() {

        service.update(getEntity());
        redirectionUrl = "homework!list.action";
        return SUCCESS;
    }

    public Homework getEntity() {
        return entity;
    }

    public void setEntity(Homework entity) {
        this.entity = entity;
    }

}