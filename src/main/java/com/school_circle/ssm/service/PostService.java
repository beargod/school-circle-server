package com.school_circle.ssm.service;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.model.ScPost;
import com.school_circle.ssm.model.ScReply;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by chentz on 2017/3/27.
 */
public interface PostService {
    void publishPost(long userId,String title, String content, String type, MultipartFile[] files) throws SchoolCircleException;

    void replyPost(long userId,String content,Byte postType,Long replyTo,String rootPostId,String parentReplyId, MultipartFile[] files) throws SchoolCircleException;

    void deletePost(String postId) throws SchoolCircleException;

    void deleteReply(String deleteReply) throws SchoolCircleException;

    ScReply getReplyById(String replyId) throws SchoolCircleException;

    ScPost getPostById(String postId) throws SchoolCircleException;

    List<ScReply> getReplyByRootPostId(String postId) throws SchoolCircleException;

    List<ScReply> getReplyByParentPostId(String postId) throws SchoolCircleException;

    List<ScPost> getPostListByType(String postType) throws SchoolCircleException;
}
