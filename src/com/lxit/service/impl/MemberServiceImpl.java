package com.lxit.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lxit.dao.MemberDao;
import com.lxit.entity.Member;
import com.lxit.service.MemberService;
import com.lxit.util.CommonUtil;

/**
 * Service实现类 - 会员
 */

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, String> implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Resource
    public void setBaseDao(MemberDao userDao) {
        super.setBaseDao(userDao);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return memberDao.isExistByUsername(username);
    }

    @Override
    public Member getMemberByUsername(String username) {
        return memberDao.getMemberByUsername(username);
    }

    @Override
    public boolean verifyMember(String username, String password) {
        Member member = getMemberByUsername(username);
        if (member != null && member.getPassword().equals(DigestUtils.md5Hex(password))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String buildPasswordRecoverKey() {
        return System.currentTimeMillis() + Member.PASSWORD_RECOVER_KEY_SEPARATOR + CommonUtil.getUUID()
                + DigestUtils.md5Hex(CommonUtil.getRandomString(10));
    }

    @Override
    public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
        long time = Long
                .valueOf(StringUtils.substringBefore(passwordRecoverKey, Member.PASSWORD_RECOVER_KEY_SEPARATOR));
        return new Date(time);
    }

}