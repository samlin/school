package net.shopxx.action.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.shopxx.entity.Admin;
import net.shopxx.entity.Resource;
import net.shopxx.entity.Role;
import net.shopxx.service.ResourceService;
import net.shopxx.service.RoleService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 角色
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1E2CFCD1B4E7FF65077CA49EB3FAE650
 * ============================================================================
 */

@ParentPackage("admin")
public class RoleAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Role role;
	private String[] resourceIds;
	private List<Resource> allResource;

	@javax.annotation.Resource
	private RoleService roleService;
	@javax.annotation.Resource
	private ResourceService resourceService;

	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = role.getName();
		if (roleService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 是否已存在 ajax验证
	public String checkValue() {
		String oldValue = getParameter("oldValue");
		String newValue = role.getValue();
		if (roleService.isUnique("value", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 列表
	public String list() {
		pager = roleService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception{
		for (String id : ids) {
			Role role = roleService.load(id);
			Set<Admin> adminSet = role.getAdminSet();
			if (adminSet != null && adminSet.size() > 0) {
				return ajaxJsonErrorMessage("角色[" + role.getName() + "]下存在管理员，删除失败！");
			}
		}
		roleService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		role = roleService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!"),
			@RequiredStringValidator(fieldName = "role.value", message = "角色标识不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "role.value", minLength = "6", message = "角色标识长度不能小于${minLength}!") 
		}, 
		regexFields = { 
			@RegexFieldValidator(fieldName = "role.value", expression = "^ROLE_.*", message = "角色标识必须以ROLE_开头!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (resourceIds != null && resourceIds.length > 0) {
			Set<Resource> resourceSet = new HashSet<Resource>(resourceService.get(resourceIds));
			role.setResourceSet(resourceSet);
		} else {
			role.setResourceSet(null);
		}
		roleService.save(role);
		redirectionUrl = "role!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!"),
			@RequiredStringValidator(fieldName = "role.value", message = "角色标识不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "role.value", minLength = "6", message = "角色标识长度不能小于${minLength}!") 
		}, 
		regexFields = { 
			@RegexFieldValidator(fieldName = "role.value", expression = "^ROLE_.*", message = "角色标识必须以ROLE_开头!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Role persistent = roleService.load(id);
		if (persistent.getIsSystem()) {
			addActionError("系统内置角色不允许修改!");
			return ERROR;
		}
		if (resourceIds != null && resourceIds.length > 0) {
			Set<Resource> resourceSet = new HashSet<Resource>(resourceService.get(resourceIds));
			role.setResourceSet(resourceSet);
		} else {
			role.setResourceSet(null);
		}
		BeanUtils.copyProperties(role, persistent, new String[] {"id", "createDate", "modifyDate", "isSystem", "adminSet"});
		roleService.update(persistent);
		redirectionUrl = "role!list.action";
		return SUCCESS;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String[] resourceIds) {
		this.resourceIds = resourceIds;
	}

	public List<Resource> getAllResource() {
		allResource = resourceService.getAll();
		return allResource;
	}

	public void setAllResource(List<Resource> allResource) {
		this.allResource = allResource;
	}

}