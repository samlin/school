package com.lxit.action.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.Ip;
import com.lxit.service.IpService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class IpAction extends BaseAdminAction {
	@Resource
	private IpService ipService;
	private Ip ip;

	public IpService getIpService() {
		return ipService;
	}

	public void setIpService(IpService ipService) {
		this.ipService = ipService;
	}

	public Ip getIp() {
		return ip;
	}

	public void setIp(Ip ip) {
		this.ip = ip;
	}

	// 列表
	public String list() {
		pager = ipService.findByPager(pager);
		return LIST;
	}

	public String edit() {
		ip = ipService.load(id);
		return INPUT;
	}

	public String delete() {
		ipService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	@InputConfig(resultName = "error")
	public String update() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Ip persistent = ipService.load(request.getParameter("id"));
		persistent.setNetID(ip.getNetID());
		persistent.setUserName(ip.getUserName());
		persistent.setClasses(ip.getClasses());
		ipService.update(persistent);
		redirectionUrl = "ip!list.action";
		return SUCCESS;
	}

	public String add() {
		return INPUT;
	}

	public String save() {
		ipService.save(ip);
		redirectionUrl = "ip!list.action";
		return SUCCESS;
	}
}
