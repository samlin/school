package com.lxit.action.shop;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.lxit.service.MemberService;
import com.lxit.service.MessageService;

/**
 * 前台Action类 - 会员中心
 */

@ParentPackage("member")
public class MemberCenterAction extends BaseShopAction {

    private static final long serialVersionUID = -3568504222758246021L;

    @Resource
    MemberService memberService;
    @Resource
    MessageService messageService;

    // 会员中心首页
    public String index() {
        return "index";
    }

    // 获取未读消息数量
    public Long getUnreadMessageCount() {
        return messageService.getUnreadMessageCount(getLoginMember());
    }

}