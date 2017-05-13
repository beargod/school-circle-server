package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.mapper.ScUserFriendGroupMapper;
import com.school_circle.ssm.mapper.ScUserFriendMapper;
import com.school_circle.ssm.model.ScUserFriend;
import com.school_circle.ssm.model.ScUserFriendGroup;
import com.school_circle.ssm.model.User;
import com.school_circle.ssm.model.UserShow;
import com.school_circle.ssm.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.Date;
import java.util.List;

/**
 * Created by BigGod on 2017-04-27.
 */
@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ScUserFriendMapper userFriendMapper;

    @Autowired
    private ScUserFriendGroupMapper groupMapper;

    @Override
    public void addFriend(long userId, long friendId,String remark){
        User user = userService.selectByUserId(userId);
        messageService.sendMessage(userId,friendId,"[&好友添加&]"+user.getRealName()+"向你发送了添加好友请求");
    }

    @Override
    public void confirmFriend(long userId, long friendId) {
        messageService.sendMessage(userId,friendId,"你好，我们已经是好友了");
        messageService.sendMessage(friendId,userId,"你好，我们已经是好友了");
        ScUserFriend record = new ScUserFriend();
        record.setAddTime(new Date());
        record.setFriendId(friendId);
        record.setUserId(userId);
        record.setStatus("friend");
        userFriendMapper.insert(record);
        record.setFriendId(userId);
        record.setUserId(friendId);
        userFriendMapper.insert(record);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        userFriendMapper.deleteFriend(userId,friendId);
    }

    @Override
    public void changeRemark(long userId, long friendId, String remark) {
        userFriendMapper.changeRemark(userId,friendId,remark);
    }

    @Override
    public void changeGroup(long userId, long friendId, String groupId) {

    }

    @Override
    public void addGroup(long userId, String groupName) {
        ScUserFriendGroup group = new ScUserFriendGroup();
        group.setGroupName(groupName);
        group.setGroupNumber(0L);
        group.setUserId(userId);
        groupMapper.insert(group);
    }

    @Override
    public void deleteGroup(long groupId) {
        groupMapper.deleteByPrimaryKey(groupId);
    }

    @Override
    public void changeGroupName(long groupId, String groupName) {

        groupMapper.changeGroupName(groupName,groupId);
    }

    @Override
    public List<UserShow> getFriendListByGroup( long groupId) {
        return userFriendMapper.selectByGroup(groupId);
    }

    @Override
    public List<UserShow> getFriendListByUserId(long userId) {
       return userFriendMapper.selectByUserId(userId);
    }

    @Override
    public List<ScUserFriendGroup> getFriendGroup(long userId) {
        return null;
    }
}
