package com.school_circle.ssm.controller;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.service.VerifyCodeService;
import com.school_circle.ssm.service.impl.VerifyCodeServiceImpl;
import com.school_circle.ssm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BigGod on 2017-04-27.
 */
@RequestMapping("/verifyCode")
@RestController
public class VerifyCodeResource {
    @Autowired
    VerifyCodeService verifyCodeService;

    @RequestMapping("/get")
    @ResponseBody
    public Result<?> getVerifyCode(){
        try {
            return Result.result(verifyCodeService.getVerifyCode());
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc());
        }
    }

    @RequestMapping("/verify")
    @ResponseBody
    public Result<?> verifyCode(@RequestParam("verifyString")String verifyString,
                                @RequestParam("codeId")String codeId){
        return Result.result(verifyCodeService.isVerifyPass(verifyString, codeId));
    }
}
