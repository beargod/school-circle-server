package com.school_circle.ssm.controller;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.model.ScCourseTable;
import com.school_circle.ssm.service.SchoolDataService;
import com.school_circle.ssm.service.impl.SchoolDataServiceImpl;
import com.school_circle.ssm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by chentz on 2017/3/27.
 */
@RequestMapping("/schoolData")
@RestController
public class SchoolDataResource {
    private SchoolDataService schoolDataService;

    @Autowired
    public SchoolDataResource(SchoolDataServiceImpl schoolDataService) {
        this.schoolDataService = schoolDataService;
    }

    @RequestMapping(value = "/courseTable", method = RequestMethod.GET)
    @ResponseBody
    public Result<?> getCourseTable(@RequestParam(value = "account", required = false)String account,
                                              @RequestParam(value = "password", required = false)String password,
                                              @RequestParam(value = "year", required = false)String year,
                                              @RequestParam(value = "term", required = false)String term,
                                              @RequestParam(value = "userId",required = false)long userId
                                        ){
        try {
            return Result.result(schoolDataService.addCoursesTable(account,password,year,term,userId));
        } catch (IOException e) {
            return Result.error(1,e.getMessage());
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        } catch (URISyntaxException e) {
            return Result.error(1,e.getMessage());
        }
    }
}
