package com.lxit.service;

import java.util.List;

import com.lxit.entity.Student;

public interface StudentService extends BaseService<Student, String> {
    public List<Student> getStudentList(String groupName, String className);
}
