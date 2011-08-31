package com.lxit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lxit.dao.GroupDao;
import com.lxit.entity.Groupes;

@Repository
public class GroupDaoImpl extends BaseDaoImpl<Groupes, String> implements
		GroupDao {
	@Override
	public boolean estimate(String className, String groupName) {
		String sql = "from Groupes where className=? and name=?";
		Query query = getSession().createQuery(sql);
		query.setString(0, className);
		query.setString(1, groupName);
		List<Groupes> list = query.list();
		if (list.size() > 0) {
			return false;
		} else
			return true;
	}

}
