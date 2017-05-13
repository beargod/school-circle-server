package com.school_circle.ssm.controller;

import com.school_circle.ssm.service.FriendService;
import com.school_circle.ssm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BigGod on 2017-05-02.
 */
@RequestMapping("/friend")
@RestController
public class FriendResource {

    @Autowired
    private FriendService friendService;

    @RequestMapping("/add")
    @ResponseBody
    public Result<?> addFriend(@RequestParam("userId")long userId,
                               @RequestParam("friendId")long friendId,
                               @RequestParam("remark")String remark){
        friendService.addFriend(userId,friendId,remark);
        return Result.ok();
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public Result<?> confirmFriend(@RequestParam("userId")long userId,
                               @RequestParam("friendId")long friendId){
        friendService.confirmFriend(userId,friendId);
        return Result.ok();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result<?> deleteFriend(@RequestParam("userId")long userId,
                                   @RequestParam("friendId")long friendId){
        friendService.deleteFriend(userId,friendId);
        return Result.ok();
    }

    @RequestMapping("/changeRemark")
    @ResponseBody
    public Result<?> changeRemark(@RequestParam("userId")long userId,
                               @RequestParam("friendId")long friendId,
                               @RequestParam("remark")String remark){
        friendService.changeRemark(userId,friendId,remark);
        return Result.ok();
    }

    @RequestMapping("/getList")
    @ResponseBody
    public Result<?> getFriendList(@RequestParam("userId")long userId){
        return Result.result(friendService.getFriendListByUserId(userId));
    }
}
