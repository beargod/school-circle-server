package com.school_circle.ssm.service;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.utils.VerifyCode;

/**
 * Created by BigGod on 2017-04-25.
 */
public interface VerifyCodeService {

    VerifyCode getVerifyCode() throws SchoolCircleException;

    boolean isVerifyPass(String verifyId,String verifyStr);
}
