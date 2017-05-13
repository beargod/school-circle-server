package com.school_circle.ssm.controller;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.service.MessageService;
import com.school_circle.ssm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by BigGod on 2017-05-02.
 */
@RequestMapping("/chat")
@RestController
public class ChatResource {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/send")
    @ResponseBody
    public Result<?> sendMessage(@RequestParam("userId")long userId,
                                 @RequestParam("receiveId")long receiveId,
                                 @RequestParam("message")String message){
        messageService.sendMessage(userId,receiveId,message);
        return Result.ok();
    }

    @RequestMapping("/receive")
    @ResponseBody
    public Result<?> receiveMessage(@RequestParam("userId")long userId,
                                 @RequestParam("receiveId")long receiveId){
        try {
            messageService.acceptMessage(userId,receiveId);
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
        return Result.ok();
    }

    @RequestMapping("/getMessageWithFriend")
    @ResponseBody
    public Result<?> getMessageWithFriend(@RequestParam("userId")long userId,
                                    @RequestParam("receiveId")long receiveId){
        return Result.result(messageService.getMessageFromUserId(userId,receiveId));
    }

    @RequestMapping("/getMessageList")
    @ResponseBody
    public Result<?> getMessageList(@RequestParam("userId")long userId){
        return Result.result(messageService.getUnReadMessageByUser(userId));
    }
}
