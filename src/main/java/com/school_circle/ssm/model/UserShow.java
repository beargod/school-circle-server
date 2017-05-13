package com.school_circle.ssm.model;

/**
 * Created by BigGod on 2017-04-30.
 */
public class UserShow {
    private long userId;

    private String avatar;

    private String userName;

    private String remark;

    public UserShow(long userId, String avatar, String userName, String remark) {
        this.userId = userId;
        this.avatar = avatar;
        this.userName = userName;
        this.remark = remark;
    }
}
