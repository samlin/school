package com.lxit.action.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.Subject;
import com.lxit.service.SubjectService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class SubjectAction extends BaseAdminAction {

    @Resource
    private SubjectService subjectService;
    private Subject subject;
    private String forward;
    private String back;
    private String names;
    private String values;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    // 列表
    public String list() {
        pager = subjectService.findByPager(pager, names, values);
        return LIST;
    }

    public String edit() {
        subject = subjectService.load(id);
        return INPUT;
    }

    public String delete() {
        subjectService.delete(ids);
        return ajaxJsonSuccessMessage("删除成功！");
    }

    @InputConfig(resultName = "error")
    public String update() {
        subject.setIncId(forward + back);
        HttpServletRequest request = ServletActionContext.getRequest();
        Subject persistent = subjectService.load(request.getParameter("id"));
        persistent.setIncId(subject.getIncId());
        persistent.setIssue(subject.getIssue());
        persistent.setSolution(subject.getSolution());
        subjectService.update(persistent);
        redirectionUrl = "inc!list.action";
        return SUCCESS;
    }

    public String add() {
        return INPUT;
    }

    public String save() {
        subject.setIncId(forward + back);
        subjectService.save(subject);

        redirectionUrl = "subject!list.action?id=" + subject.getIncId();
        return SUCCESS;
    }
}
