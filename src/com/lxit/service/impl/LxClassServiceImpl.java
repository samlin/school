package com.lxit.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.lxit.dao.BaseDao;
import com.lxit.dao.LxClassDao;
import com.lxit.entity.LxClass;
import com.lxit.service.LxClassService;
@Service
public class LxClassServiceImpl extends BaseServiceImpl<LxClass, String> implements LxClassService  {
	@Resource
	public void setBaseDao(LxClassDao lxClassDao) {
		super.setBaseDao(lxClassDao);
	}
}
