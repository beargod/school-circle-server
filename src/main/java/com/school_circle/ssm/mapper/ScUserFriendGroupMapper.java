package com.school_circle.ssm.mapper;

import com.school_circle.ssm.model.ScUserFriendGroup;
import java.util.List;

public interface ScUserFriendGroupMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend_group
     *
     * @mbggenerated Mon Apr 24 15:16:38 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend_group
     *
     * @mbggenerated Mon Apr 24 15:16:38 CST 2017
     */
    int insert(ScUserFriendGroup record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend_group
     *
     * @mbggenerated Mon Apr 24 15:16:38 CST 2017
     */
    ScUserFriendGroup selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend_group
     *
     * @mbggenerated Mon Apr 24 15:16:38 CST 2017
     */
    List<ScUserFriendGroup> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_friend_group
     *
     * @mbggenerated Mon Apr 24 15:16:38 CST 2017
     */
    int updateByPrimaryKey(ScUserFriendGroup record);

    int changeGroupName(String groupName,long groupId);
}