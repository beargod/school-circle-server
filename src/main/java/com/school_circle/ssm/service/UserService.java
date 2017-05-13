package com.school_circle.ssm.service;

import com.school_circle.ssm.model.User;
import com.school_circle.ssm.exception.SchoolCircleException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by chentz on 2017/2/9.
 */
public interface UserService {
    List<User> selectAllUsers();

    void addUser(String account, String password,
                 String userName,String email) throws SchoolCircleException;

    User login(String account, String password) throws SchoolCircleException;

    void loginByToken(String token) throws SchoolCircleException;

    User selectByUserId(long id);

    User selectByEmail(String email);

    User selectByTelephone(String telephone);

    User selectByAccount(String account);

    User selectByUserName(String userName);

    void uploadAvatar(long userId,MultipartFile avatar) throws SchoolCircleException;
}
