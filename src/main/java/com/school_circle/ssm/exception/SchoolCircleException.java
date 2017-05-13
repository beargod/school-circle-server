package com.school_circle.ssm.exception;

import com.school_circle.ssm.utils.Result;

/**
 * Created by chentz on 2017/2/9.
 */
public class SchoolCircleException extends Exception{
    /**
     * 用来判断错误的类型
     */
    private int rc;

    public SchoolCircleException(int rc) {
        super();
        this.rc = rc;
    }

    public SchoolCircleException(int rc,String msg) {
        super(msg);
        this.rc = rc;
    }

    public SchoolCircleException(int rc,Throwable e) {
        super(e);
        this.rc=rc;
    }

    public SchoolCircleException(int rc,String msg, Throwable e) {
        super(msg, e);
    }

    public int getRc(){
        return this.rc;
    }

    public Result toResult() {
        return Result.error(this.getRc(), this.getMessage());
    }
}
