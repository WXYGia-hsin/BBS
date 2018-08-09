package com.wxy.bbs.dao;

import com.wxy.bbs.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IArticleDao extends CrudRepository<Article,Integer> {
    @Query("select c from Article c where rootid=:rootid")
    Page<Article> findAll(Pageable pageable, @Param("rootid") Integer rootid);
}
