package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.mapper.ScUserSchoolDataMapper;
import com.school_circle.ssm.model.ScUserSchoolData;
import com.school_circle.ssm.service.UserSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chentz on 2017/3/13.
 */
@Service
public class UserSchoolServiceImpl implements UserSchoolService{

    @Autowired
    private ScUserSchoolDataMapper mapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScUserSchoolData record) {

        return mapper.insert(record);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    @Override
    public ScUserSchoolData selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public ScUserSchoolData selectByUserId(String userId) {
        return mapper.selectByUserId(userId);
    }

    @Override
    public List<ScUserSchoolData> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(ScUserSchoolData record) {
        return mapper.updateByPrimaryKey(record);
    }

}
