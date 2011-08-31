package com.lxit.action.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.entity.Groupes;
import com.lxit.entity.LxClass;
import com.lxit.entity.Student;
import com.lxit.service.GroupService;
import com.lxit.service.LxClassService;
import com.lxit.service.StudentService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@ParentPackage("admin")
public class GroupAction extends BaseAdminAction {
    @Resource
    private LxClassService lxClassService;
    @Resource
    private GroupService groupService;
    @Resource
    private StudentService studentService;
    private Groupes group;
    private String name;
    private String className;
    private List<Groupes> groupList;
    private List<Student> studentList;
    private List<LxClass> lxClassList;
    public static final String LEAGUER = "leaguer";

    public List<LxClass> getLxClassList() {
        return lxClassList;
    }

    public void setLxClassList(List<LxClass> lxClassList) {
        this.lxClassList = lxClassList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setGroupList(List<Groupes> groupList) {
        this.groupList = groupList;
    }

    public GroupService getService() {
        return groupService;
    }

    public void setService(GroupService groupService) {
        this.groupService = groupService;
    }

    public Groupes getGroup() {
        return group;
    }

    public void setGroup(Groupes group) {
        this.group = group;
    }

    // 列表
    public String list() {
        pager = groupService.findByPager(pager);
        return LIST;
    }

    public String edit() {
        group = groupService.load(id);
        return INPUT;
    }

    public String delete() {
        groupService.delete(ids);
        return ajaxJsonSuccessMessage("删除成功！");
    }

    @InputConfig(resultName = "error")
    public String update() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Groupes persistent = groupService.load(request.getParameter("id"));
        persistent.setName(group.getName());
        persistent.setClassName(group.getClassName());
        persistent.setDepict(group.getDepict());
        groupService.update(persistent);
        redirectionUrl = "group!list.action";
        return SUCCESS;
    }

    public String add() {
        lxClassList = lxClassService.getAll();
        return INPUT;
    }

    public List<Groupes> getGroupList() {
        groupList = groupService.getAll();
        return groupList;
    }

    public String leaguer() {
        pager = studentService.findByPager(pager);
        studentList = studentService.getStudentList(name, className);
        return LEAGUER;
    }

    public String save() {
        if (groupService.estimate(group.getClassName(), group.getName())) {
            groupService.save(group);
        } else {
            System.out.println("此班级已有此组，马上要毕业了,所以没什么时间做页面了。");
        }
        redirectionUrl = "group!list.action";
        return SUCCESS;
    }
}
