package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.mapper.ScVerifyCodeMapper;
import com.school_circle.ssm.model.ScVerifyCode;
import com.school_circle.ssm.service.VerifyCodeService;
import com.school_circle.ssm.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.io.IOException;
/**
 * Created by BigGod on 2017-04-25.
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService{
    @Autowired
    private ScVerifyCodeMapper mapper;

    @Override
    public VerifyCode getVerifyCode() throws SchoolCircleException {
        try {
            VerifyCode verifyCode = VerifyCode.build();
            mapper.insert(new ScVerifyCode(verifyCode));
            return verifyCode;
        } catch (IOException e) {
            throw new SchoolCircleException(201,"获取验证码失败");
        }
    }

    @Override
    public boolean isVerifyPass(String verifyId, String verifyStr) {
        ScVerifyCode verifyCode = mapper.selectByPrimaryKey(verifyId);
        if(verifyCode==null)
            return false;
        return verifyCode.getCodeString().equals(verifyStr);
    }

}
