package net.shopxx.action.admin;

import java.io.File;

import javax.annotation.Resource;

import net.shopxx.bean.SystemConfig;
import net.shopxx.entity.FriendLink;
import net.shopxx.service.FriendLinkService;
import net.shopxx.util.CommonUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 友情链接
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2C31672600793CF1B08F7481F47E77D6
 * ============================================================================
 */

@ParentPackage("admin")
public class FriendLinkAction extends BaseAdminAction {
	
	private static final long serialVersionUID = -1618646588525569834L;
	
	private FriendLink friendLink;
	private File logo;
	private String logoFileName;

	@Resource
	private FriendLinkService friendLinkService;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		friendLink = friendLinkService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = friendLinkService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		friendLinkService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "friendLink.name", message = "友情链接名称不允许为空!"),
			@RequiredStringValidator(fieldName = "friendLink.url", message = "链接地址不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "friendLink.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "friendLink.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
		if (StringUtils.isEmpty(allowedUploadImageExtension)){
			addActionError("不允许上传图片文件!");
			return ERROR;
		}
		String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
		if (logo != null) {
			String logoExtension = StringUtils.substringAfterLast(logoFileName, ".").toLowerCase();
			if (!ArrayUtils.contains(imageExtensionArray, logoExtension)) {
				addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
				return ERROR;
			}
		}
		int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
		if (uploadLimit != 0) {
			if (logo != null && logo.length() > uploadLimit) {
				addActionError("Logo文件大小超出限制!");
				return ERROR;
			}
		}
		if (logo != null) {
			String logoFilePath = SystemConfig.UPLOAD_IMAGE_DIR + CommonUtil.getUUID() + "." + StringUtils.substringAfterLast(logoFileName, ".").toLowerCase();
			File logoFile = new File(ServletActionContext.getServletContext().getRealPath(logoFilePath));
			FileUtils.copyFile(logo, logoFile);
			friendLink.setLogo(logoFilePath);
		}
		friendLinkService.save(friendLink);
		redirectionUrl = "friend_link!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "friendLink.name", message = "友情链接名称不允许为空!"),
			@RequiredStringValidator(fieldName = "friendLink.url", message = "链接地址不允许为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "friendLink.orderList", message = "排序不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "friendLink.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		FriendLink persistent = friendLinkService.load(id);
		String allowedUploadImageExtension = getSystemConfig().getAllowedUploadImageExtension().toLowerCase();
		if (StringUtils.isEmpty(allowedUploadImageExtension)){
			addActionError("不允许上传图片文件!");
			return ERROR;
		}
		String[] imageExtensionArray = allowedUploadImageExtension.split(SystemConfig.EXTENSION_SEPARATOR);
		if (logo != null) {
			String logoExtension = StringUtils.substringAfterLast(logoFileName, ".").toLowerCase();
			if (!ArrayUtils.contains(imageExtensionArray, logoExtension)) {
				addActionError("只允许上传图片文件类型: " + allowedUploadImageExtension + "!");
				return ERROR;
			}
		}
		int uploadLimit = getSystemConfig().getUploadLimit() * 1024;
		if (uploadLimit != 0) {
			if (logo != null && logo.length() > uploadLimit) {
				addActionError("Logo文件大小超出限制!");
				return ERROR;
			}
		}
		if (logo != null) {
			String logoFilePath = null;
			if (persistent.getLogo() != null) {
				logoFilePath = persistent.getLogo();
			} else {
				logoFilePath = SystemConfig.UPLOAD_IMAGE_DIR + CommonUtil.getUUID() + "." + StringUtils.substringAfterLast(logoFileName, ".").toLowerCase();
			}
			File logoFile = new File(ServletActionContext.getServletContext().getRealPath(logoFilePath));
			FileUtils.copyFile(logo, logoFile);
			persistent.setLogo(logoFilePath);
		}
		BeanUtils.copyProperties(friendLink, persistent, new String[]{"id", "createDate", "modifyDate", "logo"});
		friendLinkService.update(persistent);
		redirectionUrl = "friend_link!list.action";
		return SUCCESS;
	}

	public FriendLink getFriendLink() {
		return friendLink;
	}

	public void setFriendLink(FriendLink friendLink) {
		this.friendLink = friendLink;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

}