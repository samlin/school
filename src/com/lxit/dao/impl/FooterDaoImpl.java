package com.lxit.dao.impl;

import org.springframework.stereotype.Repository;

import com.lxit.dao.FooterDao;
import com.lxit.entity.Footer;

/**
 * Dao实现类 - 网页底部信息
 */

@Repository
public class FooterDaoImpl extends BaseDaoImpl<Footer, String> implements FooterDao {

    @Override
    public Footer getFooter() {
        return load(Footer.FOOTER_ID);
    }

}
