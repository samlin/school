package com.lxit.action.admin;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.atlassian.jira.rpc.soap.client.RemoteIssue;
import com.lxit.entity.Interview;
import com.lxit.service.InterviewService;
import com.lxit.util.IssueConvert;
import com.lxitedu.service.jira.InterviewJiraService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class InterviewAction extends BaseAdminAction {

    private static final long serialVersionUID = -5383463207248344967L;

    private Interview entity;

    @Resource
    private InterviewService service;

    // 查看
    public String importInterviews() {
        InterviewJiraService interviewJiraService = new InterviewJiraService();
        RemoteIssue[] interviewIssues = interviewJiraService.getInterviewIssues();
        IssueConvert issueConvert = new InterviewIssueConvert();
        for (int i = 0; i < interviewIssues.length; i++) {
            Interview interview = (Interview) issueConvert.convertIssueToBean(interviewIssues[i]);
            service.save(interview);
        }
        redirectionUrl = "interview!list.action";
        return SUCCESS;
    }

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

        redirectionUrl = "interview!list.action";
        return SUCCESS;
    }

    @InputConfig(resultName = "error")
    public String update() {

        service.update(getEntity());
        redirectionUrl = "interview!list.action";
        return SUCCESS;
    }

    public Interview getEntity() {
        return entity;
    }

    public void setEntity(Interview entity) {
        this.entity = entity;
    }

}