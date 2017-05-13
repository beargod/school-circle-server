package com.school_circle.ssm.service;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.model.ScCourseTable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by chentz on 2017/3/2.
 * 获取校内信息
 */
public interface SchoolDataService {
    void loginSchoolWeb(String account, String password) throws IOException, SchoolCircleException;

    List<ScCourseTable> addCoursesTable(String account, String password, String year, String term, Long userId) throws IOException, SchoolCircleException, URISyntaxException;
}
