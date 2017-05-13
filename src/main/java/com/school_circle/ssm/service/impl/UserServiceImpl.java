package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.mapper.ScLoginMapper;
import com.school_circle.ssm.mapper.UserMapper;
import com.school_circle.ssm.model.ScLogin;
import com.school_circle.ssm.service.UserService;
import com.school_circle.ssm.model.User;
import com.school_circle.ssm.utils.Regular;
import com.school_circle.ssm.exception.SchoolCircleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chentz on 2017/2/9.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userInfoMapper;
    @Autowired
    private ScLoginMapper scLoginMapper;

    public static String location = "file:///D:/school-circle/static/img/avatar";

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<User> selectAllUsers() {
        return userInfoMapper.selectAllUsers();
    }

    public void addUser(String account, String password, String userName, String email) throws SchoolCircleException {
        Pattern pattern2 = Pattern.compile(Regular.EMAIL_EXPRESSION);
        Matcher matcher2 = pattern2.matcher(email);
        if(!matcher2.find())
            throw new SchoolCircleException(2,"创建失败,错误的邮箱格式");
        if(null==account)
            throw new SchoolCircleException(3,"创建失败,账号不能为空");
        if(null==password)
            throw new SchoolCircleException(4,"创建失败,密码不能为空");
        if(null==userName)
            throw new SchoolCircleException(5,"创建失败,用户昵称不能为空");
        if(null!=selectByUserName(userName))
            throw new SchoolCircleException(6,"创建失败,用户昵称"+userName+"已存在");
        if(null!=selectByAccount(account))
            throw new SchoolCircleException(7,"创建失败,账号"+account+"已存在");
        if(null!=selectByEmail(email))
            throw new SchoolCircleException(8,"创建失败,邮箱"+email+"已存在");

        userInfoMapper.addUser(account,password,userName,email,new Date(),"CREATE");
    }

    public User login(String account,String password) throws SchoolCircleException {
        User user = userInfoMapper.login(account,password);
        if(user==null)
            throw new SchoolCircleException(10,"账号或密码错误");
        String token = UUID.randomUUID().toString();
        ScLogin scLogin = new ScLogin();
        scLogin.setToken(token);
        scLogin.setUpdateTime(new Date());
        scLogin.setUserId(user.getId());
        scLoginMapper.insert(scLogin);
        user.setToken(token);
        return user;
    }

    @Override
    public void loginByToken(String token) throws SchoolCircleException {
        ScLogin login = scLoginMapper.selectByToken(token);
        long tokenTime = login.getUpdateTime().getTime();
        long nowTime = (new Date()).getTime();
        if(login==null||(nowTime-tokenTime)>1000*60*60*24*7){
            throw new SchoolCircleException(11,"请重新登入");
        }
    }

    @Override
    public User selectByUserId(long id){
        return userInfoMapper.selectByUserId(id);
    }

    @Override
    public User selectByEmail(String email){
        return userInfoMapper.selectByEmail(email);
    }

    @Override
    public User selectByTelephone(String telephone){
        return userInfoMapper.selectByTelephone(telephone);
    }

    @Override
    public User selectByAccount(String account){
        return userInfoMapper.selectByAccount(account);
    }

    @Override
    public User selectByUserName(String userName){
        return userInfoMapper.selectByUserName(userName);
    }

    @Override
    public void uploadAvatar(long userId,MultipartFile avatar) throws SchoolCircleException {
        String fileId = UUID.randomUUID().toString();
        String name = location+fileId+avatar.getContentType();
        try {
            avatar.transferTo(new File(name));
        } catch (IOException e) {
            throw new SchoolCircleException(202,"上传文件失败");
        }
        userInfoMapper.uploadAvatar(userId,name);
    }
}
