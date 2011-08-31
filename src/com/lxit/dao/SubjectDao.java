package com.lxit.dao;

import java.util.List;

import com.lxit.bean.Pager;
import com.lxit.entity.Subject;

public interface SubjectDao extends BaseDao<Subject, String> {
	public List<Subject> getSubjectList(String incId);

	public Pager findByPager(Pager pager, String name, String value);
}
