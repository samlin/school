package com.lxit.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.${className};
import com.lxit.service.${className}Service;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class ${className}Action extends BaseAdminAction {

    private static final long serialVersionUID = -5383463207248344967L;

    private ${className} entity;

    @Resource
    private ${className}Service service;


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

        redirectionUrl = "${className ? uncap_first}!list.action";
        return SUCCESS;
    }

    @InputConfig(resultName = "error")
    public String update() {

        service.update(getEntity());
        redirectionUrl = "${className ? uncap_first}!list.action";
        return SUCCESS;
    }

    public ${className} getEntity() {
        return entity;
    }

    public void setEntity(${className} entity) {
        this.entity = entity;
    }

}