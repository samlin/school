package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.GroupDao;
import com.lxit.entity.Groupes;
import com.lxit.service.GroupService;

@Service
public class GroupServiceImpl extends BaseServiceImpl<Groupes, String>
		implements GroupService {
	@Resource
	GroupDao groupDao;

	@Resource
	public void setBaseDao(GroupDao logConfigDao) {
		super.setBaseDao(logConfigDao);
	}

	@Override
	public boolean estimate(String className, String groupName) {
		return groupDao.estimate(className, groupName);
	}

}
