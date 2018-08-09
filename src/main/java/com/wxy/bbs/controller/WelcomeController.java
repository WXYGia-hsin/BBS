package com.wxy.bbs.controller;

import com.wxy.bbs.config.FreemarkerConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="w1",
        urlPatterns = {"/welcome"},
        initParams = {
                @WebInitParam(name="index",value = "article?action=queryAll&cur=0")
        })
public class WelcomeController extends HttpServlet {
    private Map<String,String> forwards;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       FreemarkerConfig.forward(resp,forwards.get("index"),null);
        RequestDispatcher requestDispatcher=req.getRequestDispatcher(forwards.get("index"));
        requestDispatcher.forward(req,resp);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        forwards=new HashMap<String,String>();
        forwards.put("index",config.getInitParameter("index"));
    }
}
