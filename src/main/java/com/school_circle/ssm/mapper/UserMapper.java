package com.school_circle.ssm.mapper;

import com.school_circle.ssm.model.User;
import com.school_circle.ssm.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper extends MyMapper<User> {
    List<User> selectAllUsers();

    void addUser(@Param("account") String account, @Param("password") String password,
                 @Param("userName") String userName, @Param("email") String email,
                 @Param("createOn") Date create_on, @Param("status") String status);

    int uploadAvatar(long userId,String avatarId);

    User login(@Param("account") String account, @Param("password") String password);

    User selectByUserId(long id);

    User selectByEmail(@Param("email")String email);

    User selectByTelephone(@Param("telephone")String telephone);

    User selectByAccount(@Param("account")String account);

    User selectByUserName(@Param("userName")String userName);
}
