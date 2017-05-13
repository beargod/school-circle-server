package com.school_circle.ssm.mapper;

import com.school_circle.ssm.model.ScUserFriend;
import com.school_circle.ssm.model.UserShow;

import java.util.List;

public interface ScUserFriendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend
     *
     * @mbggenerated Fri Mar 24 16:55:15 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend
     *
     * @mbggenerated Fri Mar 24 16:55:15 CST 2017
     */
    int insert(ScUserFriend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend
     *
     * @mbggenerated Fri Mar 24 16:55:15 CST 2017
     */
    ScUserFriend selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend
     *
     * @mbggenerated Fri Mar 24 16:55:15 CST 2017
     */
    List<ScUserFriend> selectAll();

    List<ScUserFriend> selectByChat(long userId);

    List<UserShow> selectByGroup(long groupId);

    List<UserShow> selectByUserId(long userId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend
     *
     * @mbggenerated Fri Mar 24 16:55:15 CST 2017
     */
    int updateByPrimaryKey(ScUserFriend record);

    int deleteFriend(long userId,long friendId);

    int changeRemark(long userId,long friendId,String remark);

    int changeGroup(long userId,long friendId,String groupId);
}