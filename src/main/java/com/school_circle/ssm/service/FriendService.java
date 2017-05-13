package com.school_circle.ssm.service;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.model.ScUserFriend;
import com.school_circle.ssm.model.ScUserFriendGroup;
import com.school_circle.ssm.model.User;
import com.school_circle.ssm.model.UserShow;

import java.util.List;

/**
 * Created by BigGod on 2017-04-27.
 */
public interface FriendService {
    void addFriend(long userId,long friendId,String remark);

    void confirmFriend(long userId,long friendId);

    void deleteFriend(long userId,long friendId);

    void changeRemark(long userId,long friendId,String remark);

    void changeGroup(long userId,long friendId,String groupId);

    void addGroup(long userId,String groupName);

    void deleteGroup(long groupId);

    void changeGroupName(long groupId,String groupName);

    List<UserShow> getFriendListByGroup(long groupId);

    List<UserShow> getFriendListByUserId(long userId);

    List<ScUserFriendGroup> getFriendGroup(long userId);
}
