package com.wxy.bbs.service;

import com.wxy.bbs.dao.IArticleDao;
import com.wxy.bbs.po.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleServiceImpl  {
    @Autowired
    IArticleDao iArticleDao;
    public Page<Article> findAll(Pageable pageable,Integer rootid){
        return iArticleDao.findAll(pageable,rootid);
    }


}
