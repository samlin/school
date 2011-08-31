package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.IncDao;
import com.lxit.entity.Inc;
import com.lxit.service.IncService;

@Service
public class IncServiceImpl extends BaseServiceImpl<Inc, String> implements
		IncService {
	@Resource
	IncDao incDao;

	@Resource
	public void setBaseDao(IncDao logConfigDao) {
		super.setBaseDao(logConfigDao);
	}
}
