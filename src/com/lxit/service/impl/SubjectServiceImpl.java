package com.lxit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.bean.Pager;
import com.lxit.dao.SubjectDao;
import com.lxit.entity.Subject;
import com.lxit.service.SubjectService;

@Service
public class SubjectServiceImpl extends BaseServiceImpl<Subject, String>
		implements SubjectService {
	@Resource
	private SubjectDao subjectDao;

	@Resource
	public void setBaseDao(SubjectDao subjectDao) {
		super.setBaseDao(subjectDao);
	}

	@Override
	public List<Subject> getSubjectList(String incId) {
		return subjectDao.getSubjectList(incId);
	}

	@Override
	public Pager findByPager(Pager pager, String name, String value) {
		// TODO Auto-generated method stub
		return subjectDao.findByPager(pager, name, value);
	}
}
