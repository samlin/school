package com.lxit.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.Inc;
import com.lxit.entity.Subject;
import com.lxit.service.IncService;
import com.lxit.service.SubjectService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class IncAction extends BaseAdminAction {
	@Resource
	private IncService incService;
	private Inc inc;
	@Resource
	private SubjectService subjectService;
	private List<Subject> subjectList;

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	// 列表
	public String list() {
		pager = incService.findByPager(pager);
		return LIST;
	}

	public String edit() {
		inc = incService.load(id);
		return INPUT;
	}

	public String delete() {
		incService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	@InputConfig(resultName = "error")
	public String update() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Inc persistent = incService.load(request.getParameter("id"));
		persistent.setName(inc.getName());
		persistent.setPhone(inc.getPhone());
		persistent.setAddress(inc.getAddress());
		incService.update(persistent);
		redirectionUrl = "inc!list.action";
		return SUCCESS;
	}

	public Inc getInc() {
		return inc;
	}

	public void setInc(Inc inc) {
		this.inc = inc;
	}

	public String add() {
		return INPUT;
	}

	public String save() {
		incService.save(inc);
		redirectionUrl = "admin!list.action";
		return SUCCESS;
	}

	public String subject() {
		subjectList = subjectService.getSubjectList(id);
		pager = subjectService.findByPager(pager);
		return "subject";
	}
}
