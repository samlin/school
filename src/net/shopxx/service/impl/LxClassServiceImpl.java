package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.BaseDao;
import net.shopxx.dao.LxClassDao;
import net.shopxx.entity.LxClass;
import net.shopxx.service.LxClassService;

import org.springframework.stereotype.Service;
@Service
public class LxClassServiceImpl extends BaseServiceImpl<LxClass, String> implements LxClassService  {
	@Resource
	public void setBaseDao(LxClassDao lxClassDao) {
		super.setBaseDao(lxClassDao);
	}
}
