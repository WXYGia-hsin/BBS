package com.wxy.bbs.controller;

import com.wxy.bbs.config.FreemarkerConfig;
import com.wxy.bbs.po.Article;
import com.wxy.bbs.po.User;
import com.wxy.bbs.service.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "/a",urlPatterns = {"/article"},initParams = {@WebInitParam(name = "show",value = "show.ftl")})
public class ArticleController extends HttpServlet {
    private HashMap<String,String> map=new HashMap<String,String>();
    @Autowired
    private ArticleServiceImpl articleService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action=req.getParameter("action");
      switch (action){
          case "queryAll":
              User user=(User)req.getSession().getAttribute("user");
              HashMap vmap=new HashMap();
              int pageSize=0;
              if (user!=null){
                 pageSize=user.getPagenum();
                 vmap.put("msg",user.getUsername());
                 vmap.put("user",user);
              }else{
                  pageSize=10;
              }
              int curPage=Integer.parseInt(req.getParameter("cur"));
              Pageable pageable=new PageRequest(curPage,pageSize);
              Page<Article> page=articleService.findAll(pageable,0);

              vmap.put("page",page);

              FreemarkerConfig.forward(resp,map.get("show"),vmap);
              break;
      }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        map.put("show",config.getInitParameter("show"));
    }
}
