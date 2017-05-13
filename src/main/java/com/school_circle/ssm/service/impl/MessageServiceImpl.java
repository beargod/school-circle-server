package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.mapper.ScUserMessageMapper;
import com.school_circle.ssm.mapper.ScUserUnreadMessageMapper;
import com.school_circle.ssm.model.ScUserMessage;
import com.school_circle.ssm.model.ScUserUnreadMessage;
import com.school_circle.ssm.model.UserMessageItem;
import com.school_circle.ssm.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by BigGod on 2017-04-28.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ScUserMessageMapper messageMapper;

    @Autowired
    private ScUserUnreadMessageMapper unreadMapper;

    @Override
    public void sendMessage(long fromId, long receiveId, String message) {
        ScUserMessage userMessage = new ScUserMessage();
        userMessage.setFromUserId(fromId);
        userMessage.setMessageBody(message);
        userMessage.setSendTime(new Date());
        userMessage.setToUserId(receiveId);
        messageMapper.insert(userMessage);
        ScUserUnreadMessage unreadMessage = unreadMapper.selectByUserId(receiveId,fromId);
        if(unreadMessage==null){
            unreadMessage = new ScUserUnreadMessage();
            unreadMessage.setFromId(fromId);
            unreadMessage.setUserId(receiveId);
            unreadMessage.setMessageCount(1L);
            unreadMapper.insert(unreadMessage);
        }else {
            unreadMessage.setMessageCount(unreadMessage.getMessageCount()+1);
            unreadMapper.updateByPrimaryKey(unreadMessage);
        }
    }

    @Override
    public void acceptMessage(long receiveId, long fromId) throws SchoolCircleException {
        ScUserUnreadMessage unreadMessage = unreadMapper.selectByUserId(receiveId,fromId);
        if(unreadMessage==null){
            throw new SchoolCircleException(301,"找不到相关对话");
        }
        unreadMessage.setMessageCount(0L);
        unreadMapper.updateByPrimaryKey(unreadMessage);
    }

    @Override
    public List<ScUserMessage> getMessageFromUserId(long userId, long fromId) {
        List<ScUserMessage> messages = messageMapper.selectByUserId(fromId,userId);
        return messages;
    }

    @Override
    public List<UserMessageItem> getUnReadMessageByUser(long userId) {
        List<UserMessageItem> list = messageMapper.selectListByUserId(userId);
        return list;
    }
}
