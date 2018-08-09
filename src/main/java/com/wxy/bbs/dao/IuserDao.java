package com.wxy.bbs.dao;

import com.wxy.bbs.po.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IuserDao extends CrudRepository<User,Integer> {
    @Query("select c from User c where username=:u and password=:p")
    User login(@Param(value ="u") String username, @Param(value = "p") String password);

    User save(User user);

    @Query("select c from User c where userid=:id")
    User queryPictureByid(@Param(value = "id")Integer id);

    @Query("select c from User c where username=:u")
    User check(@Param(value ="u") String username);
}
