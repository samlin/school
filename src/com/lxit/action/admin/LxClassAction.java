package com.lxit.action.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.LxClass;
import com.lxit.entity.Student;
import com.lxit.service.LxClassService;
import com.lxit.service.StudentService;
import com.lxitedu.jira.http.HomeworkJiraService;
import com.lxitedu.service.jira.LxitJiraService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class LxClassAction extends BaseAdminAction {

    private static final long serialVersionUID = 3066159260207583127L;
    private List<LxClass> lxClassList;
    private LxClass lxClass;

    @Resource
    private LxClassService lxClassService;
    @Resource
    private StudentService studentService;
    private final LxitJiraService lxitJiraService = new LxitJiraService();;

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
        lxitJiraService.createGroup(id);
        List<Student> classStudentList = studentService.getList("classId", id);
        lxitJiraService.createJiraStudentsToGroup(classStudentList, id);
        return SUCCESS;
    }

    public String team() {
        HomeworkJiraService homeworkService = new HomeworkJiraService();
        homeworkService.createTeamsFromClassName(id);
        return SUCCESS;
    }

    public String dayLogProject() {
        lxitJiraService.createDayLogProject(id);
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
        lxClassService.save(getLxClass());
        redirectionUrl = "lx_class!list.action";
        return SUCCESS;
    }

    @InputConfig(resultName = "error")
    public String update() {
        LxClass lxClassTmp = getLxClass();
        lxClassTmp.setId(id);
        lxClassService.update(lxClassTmp);
        redirectionUrl = "lx_class!list.action";
        return SUCCESS;
    }

    public List<LxClass> getLxClassList() {
        lxClassList = lxClassService.getAll();
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