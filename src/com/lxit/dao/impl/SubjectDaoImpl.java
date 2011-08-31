package com.lxit.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lxit.bean.Pager;
import com.lxit.bean.Pager.OrderType;
import com.lxit.dao.SubjectDao;
import com.lxit.entity.Subject;

@Repository
public class SubjectDaoImpl extends BaseDaoImpl<Subject, String> implements
		SubjectDao {
	private Class<Subject> entityClass;

	public SubjectDaoImpl() {
		this.entityClass = null;
		Class c = getClass();
		Type type = c.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<Subject>) parameterizedType[0];
		}
	}

	@Override
	public List<Subject> getSubjectList(String incId) {
		String sql = "from Subject where incId=?";
		Query query = getSession().createQuery(sql);
		query.setString(0, incId);
		List<Subject> list = query.list();
		return list;
	}

	// 适配器模式
	@Override
	public Pager findByPager(Pager pager, String name, String value) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(entityClass);
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();

		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)
				&& StringUtils.isNotEmpty(name)
				&& StringUtils.isNotEmpty(value)) {
			String propertyString = "";
			String hejunwenString = "";
			if (property.contains(".") && name.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property,
						".");
				String propertySuffix = StringUtils.substringAfter(property,
						".");
				String hejunwenPrefix = StringUtils.substringBefore(name, ".");
				String hejunwenSuffix = StringUtils.substringBefore(name, ".");

				criteria.createAlias(propertyPrefix, "model");
				criteria.createAlias(hejunwenPrefix, "hjw");
				propertyString = "model." + propertySuffix;
				hejunwenString = "hjw." + hejunwenSuffix;
			} else {
				propertyString = property;
				hejunwenString = name;
			}
			criteria.add(Restrictions.and(
					Restrictions.like(propertyString, "%" + keyword + "%"),
					Restrictions.like(hejunwenString, "%" + value + "%")));

		}

		Integer totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}
}
