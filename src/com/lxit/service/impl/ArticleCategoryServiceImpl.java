package com.lxit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.lxit.dao.ArticleCategoryDao;
import com.lxit.entity.Article;
import com.lxit.entity.ArticleCategory;
import com.lxit.service.ArticleCategoryService;

/**
 * Service实现类 - 文章分类
 */

@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory, String> implements
        ArticleCategoryService {

    @Resource
    private ArticleCategoryDao articleCategoryDao;

    @Resource
    public void setBaseDao(ArticleCategoryDao articleCategoryDao) {
        super.setBaseDao(articleCategoryDao);
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ArticleCategory> getRootArticleCategoryList() {
        List<ArticleCategory> rootArticleCategoryList = articleCategoryDao.getRootArticleCategoryList();
        if (rootArticleCategoryList != null) {
            for (ArticleCategory rootArticleCategory : rootArticleCategoryList) {
                Hibernate.initialize(rootArticleCategory);
            }
        }
        return rootArticleCategoryList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory) {
        List<ArticleCategory> parentArticleCategoryList = articleCategoryDao
                .getParentArticleCategoryList(articleCategory);
        if (parentArticleCategoryList != null) {
            for (ArticleCategory parentArticleCategory : parentArticleCategoryList) {
                Hibernate.initialize(parentArticleCategory);
            }
        }
        return parentArticleCategoryList;
    }

    @Override
    public List<ArticleCategory> getParentArticleCategoryList(Article article) {
        ArticleCategory articleCategory = article.getArticleCategory();
        List<ArticleCategory> articleCategoryList = new ArrayList<ArticleCategory>();
        articleCategoryList.addAll(this.getParentArticleCategoryList(articleCategory));
        articleCategoryList.add(articleCategory);
        return articleCategoryList;
    }

    @Override
    public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory) {
        List<ArticleCategory> articleCategoryPathList = new ArrayList<ArticleCategory>();
        articleCategoryPathList.addAll(this.getParentArticleCategoryList(articleCategory));
        articleCategoryPathList.add(articleCategory);
        return articleCategoryPathList;
    }

    @Override
    public List<ArticleCategory> getArticleCategoryPathList(Article article) {
        ArticleCategory articleCategory = article.getArticleCategory();
        List<ArticleCategory> articleCategoryList = new ArrayList<ArticleCategory>();
        articleCategoryList.addAll(this.getParentArticleCategoryList(articleCategory));
        articleCategoryList.add(articleCategory);
        return articleCategoryList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory) {
        List<ArticleCategory> childrenArticleCategoryList = articleCategoryDao
                .getChildrenArticleCategoryList(articleCategory);
        if (childrenArticleCategoryList != null) {
            for (ArticleCategory childrenArticleCategory : childrenArticleCategoryList) {
                Hibernate.initialize(childrenArticleCategory);
            }
        }
        return childrenArticleCategoryList;
    }

    @Override
    public List<ArticleCategory> getChildrenArticleCategoryList(Article article) {
        ArticleCategory articleCategory = article.getArticleCategory();
        List<ArticleCategory> articleCategoryList = getChildrenArticleCategoryList(articleCategory);
        if (articleCategoryList == null) {
            articleCategoryList = new ArrayList<ArticleCategory>();
        }
        articleCategoryList.add(articleCategory);
        return articleCategoryList;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ArticleCategory> getArticleCategoryTreeList() {
        List<ArticleCategory> allArticleCategoryList = this.getAll();
        return recursivArticleCategoryTreeList(allArticleCategoryList, null, null);
    }

    // 递归父类排序分类树
    private List<ArticleCategory> recursivArticleCategoryTreeList(List<ArticleCategory> allArticleCategoryList,
            ArticleCategory p, List<ArticleCategory> temp) {
        if (temp == null) {
            temp = new ArrayList<ArticleCategory>();
        }
        for (ArticleCategory articleCategory : allArticleCategoryList) {
            ArticleCategory parent = articleCategory.getParent();
            if ((p == null && parent == null) || (articleCategory != null && parent == p)) {
                temp.add(articleCategory);
                if (articleCategory.getChildren() != null && articleCategory.getChildren().size() > 0) {
                    recursivArticleCategoryTreeList(allArticleCategoryList, articleCategory, temp);
                }
            }
        }
        return temp;
    }

    @Override
    @Cacheable(modelId = "caching")
    public List<ArticleCategory> getAll() {
        List<ArticleCategory> allArticleCategoryList = articleCategoryDao.getAll();
        if (allArticleCategoryList != null) {
            for (ArticleCategory articleCategory : allArticleCategoryList) {
                Hibernate.initialize(articleCategory);
            }
        }
        return allArticleCategoryList;
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(ArticleCategory articleCategory) {
        articleCategoryDao.delete(articleCategory);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String id) {
        articleCategoryDao.delete(id);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void delete(String[] ids) {
        articleCategoryDao.delete(ids);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public String save(ArticleCategory articleCategory) {
        return articleCategoryDao.save(articleCategory);
    }

    @Override
    @CacheFlush(modelId = "flushing")
    public void update(ArticleCategory articleCategory) {
        articleCategoryDao.update(articleCategory);
    }

}