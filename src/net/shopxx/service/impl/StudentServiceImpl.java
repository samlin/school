package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.StudentDao;
import net.shopxx.entity.Student;
import net.shopxx.service.StudentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 会员
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9A3ACBDD2D3A5E96E54D057769342EAF
 * ============================================================================
 */
@Transactional
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, String> implements StudentService {

	@Resource
	private StudentDao studentDao;

	@Resource
	public void setBaseDao(StudentDao studentDao) {
		super.setBaseDao(studentDao);
	}
	
	

}