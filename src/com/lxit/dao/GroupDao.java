package com.lxit.dao;

import com.lxit.entity.Groupes;

public interface GroupDao extends BaseDao<Groupes, String> {
	public boolean estimate(String className, String groupName);
}
