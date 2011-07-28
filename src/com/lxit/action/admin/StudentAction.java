package com.lxit.action.admin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.lxit.entity.Deposit;
import com.lxit.entity.Member;
import com.lxit.entity.MemberAttribute;
import com.lxit.entity.MemberRank;
import com.lxit.entity.Student;
import com.lxit.entity.Deposit.DepositType;
import com.lxit.entity.MemberAttribute.AttributeType;
import com.lxit.service.StudentService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD06041D9E5D00829FE51D5C8A9D10C0B
 * ============================================================================
 */

@ParentPackage("admin")
public class StudentAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Student student;

	@Resource
	private StudentService studentService;
	
	// 是否已存在 ajax验证
//	public String checkUsername() {
//		String username = member.getUsername();
//		if (memberService.isExistByUsername(username)) {
//			return ajaxText("false");
//		} else {
//			return ajaxText("true");
//		}
//	}

	// 查看
	public String view() {
//		member = memberService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = studentService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		
		studentService.delete(id);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		setStudent(studentService.load(id));
		return INPUT;
	}

	// 保存
//	@Validations(
//		requiredStrings = { 
//			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
//			@RequiredStringValidator(fieldName = "member.password", message = "密码不允许为空!"),
//			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
//		},
//		requiredFields = { 
//			@RequiredFieldValidator(fieldName = "member.point", message = "积分不允许为空!"),
//			@RequiredFieldValidator(fieldName = "member.deposit", message = "预存款不允许为空!"),
//			@RequiredFieldValidator(fieldName = "member.memberRank.id", message = "会员等级不允许为空!"),
//			@RequiredFieldValidator(fieldName = "member.isAccountEnabled", message = "是否启用不允许为空!")
//		},
//		stringLengthFields = {
//			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
//			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
//		}, 
//		emails = { 
//			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
//		}, 
//		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "member.point", min = "0", message = "积分必须为零或正整数!")
//		},
//		regexFields = { 
//			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
//		}
//	)
	@InputConfig(resultName = "error")
	public String save() {
		
		redirectionUrl = "student!list.action";
		return SUCCESS;
	}

	// 更新
//	@Validations(
//		requiredStrings = { 
//			@RequiredStringValidator(fieldName = "member.username", message = "用户名不允许为空!"),
//			@RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") 
//		}, 
//		requiredFields = {
//			@RequiredFieldValidator(fieldName = "member.point", message = "积分不允许为空!"),
//			@RequiredFieldValidator(fieldName = "member.deposit", message = "预存款不允许为空!"),
//			@RequiredFieldValidator(fieldName = "member.memberRank.id", message = "会员等级不允许为空!"),
//			@RequiredFieldValidator(fieldName = "member.isAccountEnabled", message = "是否启用不允许为空!")
//		}, 
//		stringLengthFields = {
//			@StringLengthFieldValidator(fieldName = "member.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
//			@StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") 
//		}, 
//		emails = { 
//			@EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") 
//		}, 
//		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "member.point", min = "0", message = "积分必须为零或正整数!")
//		},
//		regexFields = { 
//			@RegexFieldValidator(fieldName = "member.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "用户名只允许包含中文、英文、数字和下划线!") 
//		}
//	)
	@InputConfig(resultName = "error")
	public String update() {
		
		studentService.update(getStudent());
		redirectionUrl = "member!list.action";
		return SUCCESS;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}