package com.school_circle.ssm.controller;

import com.school_circle.ssm.model.User;
import com.school_circle.ssm.service.UserService;
import com.school_circle.ssm.service.impl.UserServiceImpl;
import com.school_circle.ssm.utils.Result;
import com.school_circle.ssm.exception.SchoolCircleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by chentz on 2017/2/8.
 */
@RequestMapping("/user")
@RestController
public class UserResource {
    private UserService userService;

    @Autowired
    public UserResource(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 注册
     * @param account
     * @param password
     * @param userName
     * @param email
     * @return
     */
    @RequestMapping(value = "/register", method= RequestMethod.POST)
    @ResponseBody
    public Result<?> registerUser(@RequestParam("account")String account, @RequestParam("password")String password,
                                  @RequestParam("userName")String userName,@RequestParam("email")String email
                                  ){
        try {
            userService.addUser(account,password,userName,email);
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
        return Result.ok();
    }

    /**
     * 登入
     * @param account
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result<?> login(@RequestParam("account")String account,@RequestParam("password")String password){
        User user;
        try {
            user = userService.login(account,password);
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
        return Result.result(user);
    }

    /**
     * 通过token登入
     * @param token
     * @return
     */
    @RequestMapping("/loginByToken")
    @ResponseBody
    public Result<?> loginByToken(@RequestParam("token")String token){
        try {
            userService.loginByToken(token);
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
        return Result.ok();
    }

    /**
     *
     */
    @RequestMapping("/uploadAvatar")
    @ResponseBody
    public Result<?> uploadAvatar(@RequestParam("userId")long userId,@RequestParam("avatar")MultipartFile avatar){
        try {
            userService.uploadAvatar(userId,avatar);
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
        return Result.ok();
    }
}
