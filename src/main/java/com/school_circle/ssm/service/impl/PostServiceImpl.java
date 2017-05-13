package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.mapper.ScMultipartFileMapper;
import com.school_circle.ssm.mapper.ScPostMapper;
import com.school_circle.ssm.mapper.ScReplyMapper;
import com.school_circle.ssm.model.ScMultipartFile;
import com.school_circle.ssm.model.ScPost;
import com.school_circle.ssm.model.ScReply;
import com.school_circle.ssm.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by chentz on 2017/3/27.
 */
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private ScPostMapper postMapper;
    @Autowired
    private ScReplyMapper replyMapper;
    @Autowired
    private ScMultipartFileMapper multipartFileMapper;

    public static String location = "file:///D:/school-circle/static/img/post";

    @Override
    public void publishPost(long userId, String title, String content, String type, MultipartFile[] files) throws SchoolCircleException{
        String postId = UUID.randomUUID().toString();
        content = addFiles(content,files,postId);
        ScPost post = new ScPost();
        post.setAuthorId(userId);
        post.setCotent(content);
        post.setCreateOn(new Date());
        post.setId(postId);
        post.setPostType(type);
        post.setTitle(title);
        if(files.length>0) {
            post.setThumbnail("true");
        }else {
            post.setThumbnail("false");
        }
        postMapper.insert(post);
    }

    //将富文本插入数据库中并保存在本地
    private String addFiles(String content,MultipartFile[] files,String relationId) throws SchoolCircleException {
        List<ScMultipartFile> records = new ArrayList<>();
        for(int i = 0 ;i<files.length;i++){
            MultipartFile file = files[i];
            String fileId = UUID.randomUUID().toString();
            content.replaceFirst("[&file&]",fileId);
            String name = location+fileId+file.getContentType();
            try {
                file.transferTo(new File(name));
            } catch (IOException e) {
                throw new SchoolCircleException(202,"上传文件失败");
            }
            ScMultipartFile record = new ScMultipartFile();
            record.setMultipartId(fileId);
            record.setCreateOn(new Date());
            record.setMultipartFrom(new Byte("0"));
            record.setMultipartType(new Byte("0"));
            record.setRelationId(relationId);
            records.add(record);
        }
        multipartFileMapper.insertByBatch(records);
        return content;
    }

    @Override
    public void replyPost(long userId,String content,Byte postType,Long replyTo,String rootPostId,String parentReplyId, MultipartFile[] files) throws SchoolCircleException {
        String replyId = UUID.randomUUID().toString();
        content = addFiles(content,files,replyId);
        ScReply reply = new ScReply();
        reply.setAuthorId(userId);
        reply.setCotent(content);
        reply.setCreateOn(new Date());
        reply.setId(replyId);
        reply.setPostType(postType);
        reply.setParentPostId(parentReplyId);
        reply.setRootPostId(rootPostId);
        reply.setReplyTo(replyTo);
        replyMapper.insert(reply);
    }

    @Override
    public void deletePost(String postId) throws SchoolCircleException {
        postMapper.deleteByPrimaryKey(postId);
        replyMapper.deleteByPostId(postId);
    }

    @Override
    public void deleteReply(String replyId) throws SchoolCircleException {
        replyMapper.deleteByPrimaryKey(replyId);
    }

    @Override
    public ScReply getReplyById(String replyId) throws SchoolCircleException {
        return replyMapper.selectByPrimaryKey(replyId);
    }

    @Override
    public ScPost getPostById(String postId) throws SchoolCircleException {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    public List<ScReply> getReplyByRootPostId(String postId) throws SchoolCircleException {
        return replyMapper.selectReplyListByRootPostId(postId);
    }

    @Override
    public List<ScReply> getReplyByParentPostId(String postId) throws SchoolCircleException {
        return replyMapper.selectReplyListByParentPostId(postId);
    }

    @Override
    public List<ScPost> getPostListByType(String postType) throws SchoolCircleException {
        return postMapper.selectPostListByType(postType);
    }
}
