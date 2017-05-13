package com.school_circle.ssm.mapper;

import com.school_circle.ssm.model.ScUserSchoolData;
import java.util.List;

public interface ScUserSchoolDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_school_data
     *
     * @mbggenerated Mon Mar 13 15:29:39 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_school_data
     *
     * @mbggenerated Mon Mar 13 15:29:39 CST 2017
     */
    int insert(ScUserSchoolData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_school_data
     *
     * @mbggenerated Mon Mar 13 15:29:39 CST 2017
     */
    ScUserSchoolData selectByPrimaryKey(Long id);

    ScUserSchoolData selectByUserId(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_school_data
     *
     * @mbggenerated Mon Mar 13 15:29:39 CST 2017
     */
    List<ScUserSchoolData> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_user_school_data
     *
     * @mbggenerated Mon Mar 13 15:29:39 CST 2017
     */
    int updateByPrimaryKey(ScUserSchoolData record);
}