package com.lxit.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lxit.dao.IpDao;
import com.lxit.entity.Ip;
import com.lxit.service.IpService;

@Service
public class IpServiceImpl extends BaseServiceImpl<Ip, String> implements
		IpService {
	@Resource
	IpDao ipDao;

	@Resource
	public void setBaseDao(IpDao logConfigDao) {
		super.setBaseDao(logConfigDao);
	}
}
