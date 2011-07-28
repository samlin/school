package net.shopxx.dao.impl;

import java.util.Set;

import net.shopxx.bean.Pager;
import net.shopxx.dao.StudentDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.ProductAttribute;
import net.shopxx.entity.Student;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAC8DDA6F41F51B7BA7B541180E9FE7F7
 * ============================================================================
 */

@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student, String> implements StudentDao {
	// 根据orderList排序
		@Override
		public Pager findByPager(Pager pager) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Student.class);
			return this.findByPager(pager, detachedCriteria);
		}
}