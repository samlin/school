package com.lxit.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.LxClass;
import com.lxit.entity.Student;
import com.lxit.service.LxClassService;
import com.lxit.service.StudentService;
import com.lxitedu.bean.LxitClass;
import com.lxitedu.service.jira.LxitJiraService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 商品分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF8D13DDE8B51433D52B3F73D96C223F8
 * ============================================================================
 */

@ParentPackage("admin")
public class LxClassAction extends BaseAdminAction {

	private static final long serialVersionUID = 3066159260207583127L;
	private List<LxClass> lxClassList;
	private LxClass lxClass;

	@Resource
	private LxClassService lxClassService;
	@Resource
	private StudentService studentService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		setLxClass(lxClassService.load(id));
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}

	// 删除
	public String delete() {
		lxClassService.delete(id);
		return SUCCESS;
	}
	public String dayLog() {
		LxitJiraService tt=new LxitJiraService();
		tt.createGroup(id);
		List<Student> classStudentList = studentService.getList("classId", id);
		tt.createJiraStudentsToGroup(classStudentList,id);
		return SUCCESS;
	}

	// 保存
//	@Validations(
//		requiredStrings = { 
//			@RequiredStringValidator(fieldName = "productCategory.name", message = "分类名称不允许为空!")
//		}, 
//		requiredFields = { 
//			@RequiredFieldValidator(fieldName = "productCategory.orderList", message = "排序不允许为空!")
//		},
//		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "productCategory.orderList", min = "0", message = "排序必须为零或正整数!")
//		}
//	)
	@InputConfig(resultName = "error")
	public String save() {
//		if (StringUtils.isNotEmpty(parentId)) {
//			ProductCategory parent = productCategoryService.load(parentId);
//			productCategory.setParent(parent);
//		} else {
//			productCategory.setParent(null);
//		}
		lxClassService.save(getLxClass());
		redirectionUrl = "lx_class!list.action";
		return SUCCESS;
	}

	// 更新
//	@Validations(
//		requiredStrings = { 
//			@RequiredStringValidator(fieldName = "productCategory.name", message = "分类名称不允许为空!")
//		}, 
//		requiredFields = { 
//			@RequiredFieldValidator(fieldName = "productCategory.orderList", message = "排序不允许为空!")
//		},
//		intRangeFields = {
//			@IntRangeFieldValidator(fieldName = "productCategory.orderList", min = "0", message = "排序必须为零或正整数!")
//		}
//	)
	@InputConfig(resultName = "error")
	public String update() {
//		ProductCategory persistent = productCategoryService.load(id);
//		BeanUtils.copyProperties(productCategory, persistent, new String[]{"id", "createDate", "modifyDate", "path", "parent", "children", "productSet"});
		LxClass lxClass2 = getLxClass();
		lxClass2.setId(id);
		lxClassService.update(lxClass2);
		redirectionUrl = "lx_class!list.action";
		return SUCCESS;
	}

	public List<LxClass> getLxClassList() {
		lxClassList=lxClassService.getAll();
		return lxClassList;
	}

	public void setLxClassList(List<LxClass> lxClassList) {
		this.lxClassList = lxClassList;
	}

	public LxClass getLxClass() {
		return lxClass;
	}

	public void setLxClass(LxClass lxClass) {
		this.lxClass = lxClass;
	}

}