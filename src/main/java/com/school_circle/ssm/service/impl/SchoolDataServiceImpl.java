package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.mapper.ScCourseTableMapper;
import com.school_circle.ssm.mapper.UserMapper;
import com.school_circle.ssm.model.ScCourseTable;
import com.school_circle.ssm.service.SchoolDataService;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.http.protocol.HttpCoreContext.HTTP_REQUEST;
import static org.springframework.http.HttpHeaders.*;

@Service
public class SchoolDataServiceImpl implements SchoolDataService{

    @Autowired
    private ScCourseTableMapper mapper;
    @Autowired
    private UserMapper userMapper;

    private CloseableHttpClient httpClient;
    private String regex = "_origScriptSessionId\\=\\\"[\\S]*\\\"";
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393";
    private String host = "i.cqut.edu.cn";
    private String jCookie;
    private String scriptSessionId;
    private HttpGet httpGet;
    private HttpPost httpPost;
    private String pageUrl;
    private String jwxtUrl = "http://jwxt.i.cqut.edu.cn";
    private CloseableHttpResponse response;
    private String url;
    private HttpEntity entity;
    private String html;
    private Document htmlDoc;


    @Override
    public void loginSchoolWeb(String account, String password) throws IOException, SchoolCircleException {
        httpClient = HttpClientBuilder.create().build();
        /**
         * 访问第一个url获取cookie1
         */
        url = "http://i.cqut.edu.cn";
        httpGet = new HttpGet(url);
        httpGet.addHeader(USER_AGENT,userAgent);
        response = httpClient.execute(httpGet);
        isStatusPass(response);
        if(response.getFirstHeader(SET_COOKIE)!=null)
            jCookie = response.getFirstHeader(SET_COOKIE).getValue().replace(" ","").replace("Path=/","");
        response.close();
        //获取第二个cookie
        url = "http://i.cqut.edu.cn/zfca/login";
        httpGet = new HttpGet(url);
        httpGet.addHeader(HOST, host);
        response = httpClient.execute(httpGet);
        //从页面中获取参数lt
        entity = response.getEntity();
        html = EntityUtils.toString(entity, "utf-8");
        Document doc = Jsoup.parse(html,"http://i.cqut.edu.cn");
        String lt = doc.select("[name=lt]").attr("value");
        //获取数字化校园主页地址和第三第四个cookie
        url = "http://i.cqut.edu.cn/zfca/login?service=http%3A%2F%2Fi.cqut.edu.cn%2Fportal.do";
        httpPost = new HttpPost(url);
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("username",account));
        para.add(new BasicNameValuePair("password",password));
        para.add(new BasicNameValuePair("lt",lt));
        para.add(new BasicNameValuePair("_eventId","submit"));
        para.add(new BasicNameValuePair("isremenberme","0"));
        para.add(new BasicNameValuePair("losetime","30"));
        para.add(new BasicNameValuePair("useValidateCode", "0"));
        para.add(new BasicNameValuePair("ip", ""));
        para.add(new BasicNameValuePair("submit1", ""));
        httpPost.setEntity(new UrlEncodedFormEntity(para,"utf-8"));
        response =httpClient.execute(httpPost);
        isStatusPass(response);
        //获取主页地址页面
        if(response.getFirstHeader(LOCATION)!=null)
            pageUrl = response.getFirstHeader(LOCATION).getValue();
        else
            throw new SchoolCircleException(102,"输入的学号或密码错误");
        httpGet = new HttpGet(pageUrl);
        response = httpClient.execute(httpGet);
        isStatusPass(response);
        entity = response.getEntity();
        html = EntityUtils.toString(entity, "utf-8");
        htmlDoc = Jsoup.parse(html);
        //获取scriptSessionId
        url = "http://i.cqut.edu.cn/dwr/engine.js";
        httpGet = new HttpGet(url);
        response = httpClient.execute(httpGet);
        entity = response.getEntity();
        String js = EntityUtils.toString(entity, "utf-8").replace(" ","");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(js);
        if (matcher.find()){
            scriptSessionId = matcher.group().replace("_origScriptSessionId=","").replace("\"","");
        }
    }

    /**
     * 添加课程表
     */
    @Override
    public List<ScCourseTable> addCoursesTable(String account, String password,String year,String term,Long userId) throws IOException, SchoolCircleException, URISyntaxException {
        loginSchoolWeb(account,password);
        //获取查看课程表的url地址
        url = "http://i.cqut.edu.cn/dwr/call/plaincall/portalAjax.getAppList.dwr";
        httpPost = new HttpPost(url);
        Elements elements = htmlDoc.select("a:contains(定 制)");
        String param0;
        if (elements.size()<=0)
            throw new SchoolCircleException(101,"获取课程表信息错误，请稍后再试");
        param0 = elements.get(0).getAllElements().attr("onclick").replace("show_","").replace("()","");
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("callCount","1"));
        para.add(new BasicNameValuePair("page",pageUrl));
        para.add(new BasicNameValuePair("httpSessionId",jCookie));
        para.add(new BasicNameValuePair("scriptSessionId",scriptSessionId));
        para.add(new BasicNameValuePair("c0-scriptName","portalAjax"));
        para.add(new BasicNameValuePair("c0-methodName","getAppList"));
        para.add(new BasicNameValuePair("c0-id", "0"));
        para.add(new BasicNameValuePair("c0-param0", "string:"+param0));
        para.add(new BasicNameValuePair("batchId", "20"));
        httpPost.setEntity(new UrlEncodedFormEntity(para,"utf-8"));
        response = httpClient.execute(httpPost);
        isStatusPass(response);
        entity = response.getEntity();
        String urls = EntityUtils.toString(entity, "utf-8");
        Document doc = Jsoup.parse(urls);
        String courseTableUrl = doc.select("[href*=xs_main.aspx]").attr("href").replace("\"","").replace("\\","");

        httpGet = new HttpGet(courseTableUrl);
        HttpContext context = new BasicHttpContext();
        response = httpClient.execute(httpGet,context);
        HttpUriRequest realRequest = (HttpUriRequest)context.getAttribute(HTTP_REQUEST);
        isStatusPass(response);
        entity = response.getEntity();
        html = EntityUtils.toString(entity, "utf-8");
        doc = Jsoup.parse(html);

        Elements element = doc.select("a:contains(学生个人课表)");
        if (element.size()<=0)
            throw new SchoolCircleException(101,"获取课程表信息错误，请稍后再试");
        String courseUrl = element.get(0).getAllElements().attr("href");

        String newUri = jwxtUrl+realRequest.getURI().toString().split("xs_main")[0]+courseUrl;

        httpGet = new HttpGet(newUri);
        response = httpClient.execute(httpGet);
        isStatusPass(response);
        entity = response.getEntity();
        html = EntityUtils.toString(entity, "utf-8");
        doc = Jsoup.parse(html);
        String viewState = doc.select("[name*=VIEWSTATE]").attr("value");

        httpPost = new HttpPost(newUri);
        para = new ArrayList<>();
        para.add(new BasicNameValuePair("__EVENTTARGET","xqd"));
        para.add(new BasicNameValuePair("__EVENTARGUMENT",""));
        para.add(new BasicNameValuePair("__VIEWSTATE",viewState));
        para.add(new BasicNameValuePair("xnd",year));
        para.add(new BasicNameValuePair("xqd",term));
        httpPost.setEntity(new UrlEncodedFormEntity(para,"utf-8"));
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.setHeader("Cache-Control","max-age=0");
        httpPost.setHeader("Origin","http://jwxt.i.cqut.edu.cn");
        response = httpClient.execute(httpPost);
        isStatusPass(response);
        entity = response.getEntity();
        html = EntityUtils.toString(entity, "utf-8");
        doc = Jsoup.parse(html);
        Elements table = doc.select("#Table1");
        List<ScCourseTable> contents = new ArrayList<>();
        Elements trs = table.select("tr");
        for(Element tr:trs){
            Elements tds = tr.getAllElements().select("td");
            for(int i = 0 ;i<tds.size();i++){
                contents.addAll(stringToDetail(tds.get(i).getAllElements().select("td").text(),i-1));
            }
        }
//        if(userId!=null&&userMapper.selectByUserId(userId)!=null) {
//            mapper.deleteByUserId(userId);
//            mapper.insertAll(contents);
//        }
        return contents;
    }

    private List<ScCourseTable> stringToDetail(String tdContent,int weekday){
        if(tdContent==null)
            return null;
        String getWeek = "(?<=\\{第)(.*?)(?=周)";
        String getSection = "(?<=第)(.*?)(?=节)";
        List<ScCourseTable> result = new ArrayList<>();
        ScCourseTable courseTd = new ScCourseTable();
        String[] contents = tdContent.split(" ");
        for(int i = 0 ;i<contents.length/4;i++) {
            int index = i * 4;
            courseTd.setName(contents[index+0]);
            courseTd.setTeacher(contents[index+2]);
            courseTd.setClassroom(contents[index+3]);
            //获取课程时间
            Pattern pattern=Pattern.compile(getSection);
            Matcher matcher = pattern.matcher(contents[index+1]);
            if(matcher.find()){
                String[] sections = matcher.group().split(",");
                courseTd.setClassDuration(sections.length);
                courseTd.setLesson(Integer.parseInt(sections[0]));
            }
            courseTd.setWeekday(weekday);

            pattern = Pattern.compile(getWeek);
            matcher = pattern.matcher(contents[index+1]);
            if(matcher.find()){
                String[] week = matcher.group().split("-");
                for(int j = Integer.parseInt(week[0]);j<=Integer.parseInt(week[1]);j++){
                    ScCourseTable clone = courseTd.clone();
                    clone.setWeek(j);
                    result.add(clone);
                }
            }
        }
        return result;
    }
    /**
     * 判定响应状态是否通过
     * @param response
     * @return
     */
    private void isStatusPass(CloseableHttpResponse response) throws SchoolCircleException {
        int status = response.getStatusLine().getStatusCode();
        if(200!=status&&304!=status&&302!=status){
            throw new SchoolCircleException(103,"访问url"+url+"出错");
        }
    }

}
