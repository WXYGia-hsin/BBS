package com.wxy.bbs.controller;

import com.wxy.bbs.config.FreemarkerConfig;
import com.wxy.bbs.po.User;
import com.wxy.bbs.service.UserServiceImpl;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "u",urlPatterns = {"/user"},initParams = {@WebInitParam(name = "show",value = "show.ftl")})
public class UserController extends HttpServlet {
    private Map<String,String> hashMap=new HashMap<String, String>();
    @Autowired
    private UserServiceImpl userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        hashMap.put("show",config.getInitParameter("show"));
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(req)){
            HashMap<String,Object> vmap=null;
            User user=userService.uploadPic(req);
            try(FileInputStream fileInputStream=new FileInputStream(user.getPicPath())){
                byte [] buffer=new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                user.setPicture(buffer);
                userService.save(user);
//                vmap=new HashMap<String,Object>();
//                vmap.put("msg","注册成功！");
//                vmap.put("user",user);
//                FreemarkerConfig.forward(resp,hashMap.get("show").toString(),vmap);
                RequestDispatcher dispatcher=req.getRequestDispatcher("/welcome");
                dispatcher.forward(req,resp);
            }catch (Exception e){
                e.printStackTrace();
            }


        }else{
            String action=req.getParameter("action");
            switch (action){
            case "login":
                login(req,resp);
                break;
            case "pic":
                String id=req.getParameter("id");
                User user=userService.queryPicture(Integer.parseInt(id));
                OutputStream outputStream=resp.getOutputStream();
                outputStream.write(user.getPicture());
                outputStream.flush();
                outputStream.close();
                break;
            case"check":
                check(req,resp);
                break;

            }

        }
    }


    private void login(HttpServletRequest req,HttpServletResponse resp) {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        HashMap<String,Object> vmap=null;
        User user=userService.login(username,password);
        if(user!=null){
           String sun=req.getParameter("sun");
           if(sun!=null){
               Cookie cookie1=new Cookie("papaoku",username);
               cookie1.setMaxAge(3600*24*7);
               resp.addCookie(cookie1);
               Cookie cookie2=new Cookie("papaokp",password);
               cookie2.setMaxAge(3600*24*7);
               resp.addCookie(cookie2);
           }
           req.getSession().setAttribute("user",user);
//           vmap=new HashMap<String,Object>();
//           vmap.put("user",user);
//           FreemarkerConfig.forward(resp,hashMap.get("show").toString(),vmap);
            RequestDispatcher dispatcher=req.getRequestDispatcher("/welcome");
            req.setAttribute("user",user);
            req.setAttribute("msg",username);
        }else {
//            vmap=new HashMap<String,Object>();
            FreemarkerConfig.forward(resp,hashMap.get("show").toString(),vmap);
        }
    }
    public void check(HttpServletRequest req,HttpServletResponse resp){
        String username=req.getParameter("username");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter printWriter=null;
        try {
            printWriter=resp.getWriter();
            if(userService.check(username)!=null){
                printWriter.print(true);
            }else{
                printWriter.print(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.flush();
        printWriter.close();

    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
