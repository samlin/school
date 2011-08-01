package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.LogDao;
import com.lxit.entity.Log;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log, String> implements LogDao {

}
