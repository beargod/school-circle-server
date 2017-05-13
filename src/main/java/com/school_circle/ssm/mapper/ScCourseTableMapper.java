package com.school_circle.ssm.mapper;

import com.school_circle.ssm.model.ScCourseTable;
import java.util.List;

public interface ScCourseTableMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_course_table
     *
     * @mbggenerated Fri Mar 24 14:26:08 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    int deleteByUserId(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_course_table
     *
     * @mbggenerated Fri Mar 24 14:26:08 CST 2017
     */
    int insert(ScCourseTable record);

    int insertAll(List<ScCourseTable> record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_course_table
     *
     * @mbggenerated Fri Mar 24 14:26:08 CST 2017
     */
    ScCourseTable selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_course_table
     *
     * @mbggenerated Fri Mar 24 14:26:08 CST 2017
     */
    List<ScCourseTable> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_course_table
     *
     * @mbggenerated Fri Mar 24 14:26:08 CST 2017
     */
    int updateByPrimaryKey(ScCourseTable record);
}