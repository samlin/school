package net.shopxx.action.admin;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.entity.LogConfig;
import net.shopxx.service.LogConfigService;
import net.shopxx.util.StrutsUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 日志设置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1F85E6BEB163D27BD7F7DC801AB8C0EE
 * ============================================================================
 */

@ParentPackage("admin")
public class LogConfigAction extends BaseAdminAction {

	private static final long serialVersionUID = 1294331179033448358L;

	private LogConfig logConfig;
	private Set<String> allActionClassName;

	@Resource
	private LogConfigService logConfigService;

	// ajax验证操作名称是否已存在
	public String checkOperationName() {
		String oldValue = getParameter("oldValue");
		String newValue = logConfig.getOperationName();
		if (logConfigService.isUnique("operationName", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// ajax根据Action类名称获取所有方法名称(不包含已使用的方法)
	@SuppressWarnings("unchecked")
	public String getAllActionMethod() throws ClassNotFoundException {
		String actionClassName = logConfig.getActionClassName();
		Set<String> allActionClassName = StrutsUtil.getAllActionClassName();
		if (allActionClassName.contains(actionClassName)) {
			Class actionClass = Class.forName(actionClassName);
			Method[] methods = actionClass.getDeclaredMethods();
			StringBuilder stringBuilder = new StringBuilder();
			List<LogConfig> logConfigs = logConfigService.getLogConfigList(actionClassName);
			String[] methodNameArray = new String[logConfigs.size()];
			for (int i = 0; i < logConfigs.size(); i++) {
				methodNameArray[i] = logConfigs.get(i).getActionMethodName();
			}
			for (Method method : methods) {
				if (method.getReturnType() == String.class && !ArrayUtils.contains(methodNameArray, method.getName())) {
					stringBuilder.append("<option value=\"" + method.getName() + "\">" + method.getName() + "</option>");
				}
			}
			if (stringBuilder.length() == 0) {
				stringBuilder.append("<option value=\"noValue\">无可用方法</option>");
			}
			return ajaxText(stringBuilder.toString());
		}
		return null;
	}

	// 列表
	public String list() {
		pager = logConfigService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		logConfigService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		logConfig = logConfigService.load(id);
		return INPUT;
	}

	// 保存
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "logConfig.operationName", message = "操作名称不允许为空!"),
			@RequiredStringValidator(fieldName = "logConfig.actionClassName", message = "Action类不允许为空!"),
			@RequiredStringValidator(fieldName = "logConfig.actionMethodName", message = "Action方法不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws ClassNotFoundException {
		String actionClassName = logConfig.getActionClassName();
		String actionMethodName = logConfig.getActionMethodName();
		if (!StrutsUtil.getAllActionClassName().contains(actionClassName)) {
			addActionError("Action类错误!");
			return ERROR;
		}
		Class actionClass = Class.forName(actionClassName);
		Method[] methods = actionClass.getDeclaredMethods();
		boolean isMethod = false;
		for (Method method : methods) {
			if (StringUtils.equals(method.getName(), actionMethodName)) {
				isMethod = true;
				break;
			}
		}
		if (isMethod == false) {
			addActionError("Action类错误!");
			return ERROR;
		}
		logConfigService.save(logConfig);
		redirectionUrl = "log_config!list.action";
		return SUCCESS;
	}

	// 更新
	@SuppressWarnings("unchecked")
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "logConfig.operationName", message = "操作名称不允许为空!"),
			@RequiredStringValidator(fieldName = "logConfig.actionClassName", message = "Action类不允许为空!"),
			@RequiredStringValidator(fieldName = "logConfig.actionMethodName", message = "Action方法不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws ClassNotFoundException {
		LogConfig persistent = logConfigService.load(id);
		String actionClassName = logConfig.getActionClassName();
		String actionMethodName = logConfig.getActionMethodName();
		if (!StrutsUtil.getAllActionClassName().contains(actionClassName)) {
			addActionError("Action类错误!");
			return ERROR;
		}
		Class actionClass = Class.forName(actionClassName);
		Method[] methods = actionClass.getDeclaredMethods();
		boolean isMethod = false;
		for (Method method : methods) {
			if (StringUtils.equals(method.getName(), actionMethodName)) {
				isMethod = true;
				break;
			}
		}
		if (isMethod == false) {
			addActionError("Action类错误!");
			return ERROR;
		}
		BeanUtils.copyProperties(logConfig, persistent, new String[]{"id", "createDate", "modifyDate"});
		logConfigService.update(persistent);
		redirectionUrl = "log_config!list.action";
		return SUCCESS;
	}

	public Set<String> getAllActionClassName() {
		allActionClassName = StrutsUtil.getAllActionClassName();
		return allActionClassName;
	}

	public void setAllActionClassName(Set<String> allActionClassName) {
		this.allActionClassName = allActionClassName;
	}

	public LogConfig getLogConfig() {
		return logConfig;
	}

	public void setLogConfig(LogConfig logConfig) {
		this.logConfig = logConfig;
	}

}