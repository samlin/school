package com.lxit.dao;

import java.util.List;

import com.lxit.entity.Student;

public interface StudentDao extends BaseDao<Student, String> {
    public List<Student> getStudentList(String groupName, String className);
}