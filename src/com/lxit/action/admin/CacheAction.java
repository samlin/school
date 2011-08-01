package com.lxit.action.admin;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

import freemarker.template.TemplateException;

@ParentPackage("admin")
public class CacheAction extends BaseAdminAction {

    private static final long serialVersionUID = 3290111140770511789L;

    @Resource
    private GeneralCacheAdministrator cacheManager;
    @Resource
    private FreemarkerManager freemarkerManager;

    // 刷新所有缓存
    public String flush() {
        cacheManager.flushAll();
        flushCache();
        try {
            ServletContext servletContext = ServletActionContext.getServletContext();
            freemarkerManager.getConfiguration(servletContext).clearTemplateCache();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    // 更新页面缓存
    private void flushCache() {
        Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(
                getRequest(), PageContext.APPLICATION_SCOPE);
        cache.flushAll(new Date());
    }

}