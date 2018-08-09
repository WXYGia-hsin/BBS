package com.wxy.bbs.service;
import com.wxy.bbs.dao.IuserDao;
import com.wxy.bbs.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl {
    @Autowired
    private IuserDao iuserDao;
    private Map<String, String> types = new HashMap<String, String>();

    public UserServiceImpl() {
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");
    }

    public User login(String username, String password) {

        return iuserDao.login(username, password);
    }

    public User uploadPic(HttpServletRequest req) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(4096 * 1024);
        commonsMultipartResolver.setMaxUploadSizePerFile(1024 * 1024);
        commonsMultipartResolver.setMaxUploadSize(2 * 1024 * 1024);
        MultipartHttpServletRequest multipartHttpServletRequest = commonsMultipartResolver.resolveMultipart(req);
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file0");
        System.out.println(multipartFile.getOriginalFilename());
        String type = multipartFile.getContentType();
        String filename = multipartFile.getOriginalFilename();
        String filepath = "upload" + File.separator + filename;
        File file = new File(filepath);
        User user = new User();
        try {
            multipartFile.transferTo(file);
            String username = multipartHttpServletRequest.getParameter("reusername");
            String password = multipartHttpServletRequest.getParameter("repassword");
            user.setUsername(username);
            user.setPassword(password);
            user.setPicPath(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User save(User user) {
        return iuserDao.save(user);
    }

    public User queryPicture(Integer id){
        return iuserDao.queryPictureByid(id);

    }
    public User check(@Param(value = "username") String username){
        return iuserDao.check(username);

    }

}