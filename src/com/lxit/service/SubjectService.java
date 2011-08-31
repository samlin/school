package com.lxit.service;

import java.util.List;

import com.lxit.bean.Pager;
import com.lxit.entity.Subject;

public interface SubjectService extends BaseService<Subject, String> {
	public List<Subject> getSubjectList(String incId);

	public Pager findByPager(Pager pager, String name, String value);
}
