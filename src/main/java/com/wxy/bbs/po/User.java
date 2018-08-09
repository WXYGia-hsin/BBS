package com.wxy.bbs.po;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    private int userid;
    private String username;
    private String password;
    private byte[] picture;
    private Integer pagenum;
    private String picPath;
    private Set<Article> articles;

    @Id
    @Column(name = "userid", nullable = false)
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "picture", nullable = true)
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "pagenum", nullable = true)
    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    @Basic
    @Column(name = "pic_path", nullable = true, length = 225)
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userid == user.userid &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Arrays.equals(picture, user.picture) &&
                Objects.equals(pagenum, user.pagenum) &&
                Objects.equals(picPath, user.picPath);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(userid, username, password, pagenum, picPath);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @OneToMany(mappedBy = "user")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
