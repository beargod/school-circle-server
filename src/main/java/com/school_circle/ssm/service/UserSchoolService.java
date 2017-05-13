package com.school_circle.ssm.service;

import com.school_circle.ssm.model.ScUserSchoolData;

import java.util.List;

/**
 * Created by chentz on 2017/3/13.
 */
public interface UserSchoolService {

    int deleteByPrimaryKey(Long id);

    int insert(ScUserSchoolData record);

    ScUserSchoolData selectByPrimaryKey(Long id);

    ScUserSchoolData selectByUserId(String userId);

    List<ScUserSchoolData> selectAll();

    int updateByPrimaryKey(ScUserSchoolData record);
}
