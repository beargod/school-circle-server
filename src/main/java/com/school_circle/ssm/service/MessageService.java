package com.school_circle.ssm.service;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.model.ScUserMessage;
import com.school_circle.ssm.model.ScUserUnreadMessage;
import com.school_circle.ssm.model.UserMessageItem;

import java.util.List;

/**
 * Created by BigGod on 2017-04-28.
 */
public interface MessageService {
    void sendMessage(long userId,long receiveId,String message);

    void acceptMessage(long userId,long fromId) throws SchoolCircleException;

    List<ScUserMessage> getMessageFromUserId(long userId,long fromId);

    List<UserMessageItem> getUnReadMessageByUser(long userId);
}
