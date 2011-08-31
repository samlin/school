package com.lxit.service;

import com.lxit.entity.Groupes;

public interface GroupService extends BaseService<Groupes, String> {
	public boolean estimate(String className, String groupName);
}
