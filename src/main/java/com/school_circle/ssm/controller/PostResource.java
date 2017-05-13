package com.school_circle.ssm.controller;

import com.school_circle.ssm.exception.SchoolCircleException;
import com.school_circle.ssm.service.PostService;
import com.school_circle.ssm.service.impl.PostServiceImpl;
import com.school_circle.ssm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by BigGod on 2017-04-26.
 */
@RequestMapping("/post")
@RestController
public class PostResource {
    private PostService postService;

    @Autowired
    public PostResource(PostServiceImpl postService) {
        this.postService = postService;
    }

    @RequestMapping("/publish/post")
    @ResponseBody
    public Result<?> publishPost(@RequestParam("userId") long userId, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("type") String type, @RequestParam("files") MultipartFile[] files) {
        try {
            postService.publishPost(userId, title, content, type, files);
            return Result.ok();
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(), e.getMessage());
        }
    }

    @RequestMapping("/publish/reply")
    @ResponseBody
    public Result<?> publishReply(@RequestParam("userId")long userId,@RequestParam("parentPostId")String parentPostId,@RequestParam("rootPostId")String rootPostId,@RequestParam("content")String content,@RequestParam("type")Byte type,@RequestParam("replyTo")long replyTo,@RequestParam("files")MultipartFile[] files){
        try {
            postService.replyPost(userId,content,type,replyTo,rootPostId,parentPostId,files);
            return Result.ok();
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
    }

    @RequestMapping("/delete/reply")
    @ResponseBody
    public Result<?> deleteReply(@RequestParam("replyId")String replyId){
        try {
            postService.deleteReply(replyId);
            return Result.ok();
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
    }

    @RequestMapping("/delete/post")
    @ResponseBody
    public Result<?> deletePost(@RequestParam("postId")String postId){
        try {
            postService.deletePost(postId);
            return Result.ok();
        } catch (SchoolCircleException e) {
            return Result.error(e.getRc(),e.getMessage());
        }
    }

    @RequestMapping("/reply/content")
    @ResponseBody
    public Result<?> getReplyById(@RequestParam("replyId")String replyId) throws SchoolCircleException {
        return Result.result(postService.getReplyById(replyId));
    }

    @RequestMapping("/post/content")
    @ResponseBody
    public Result<?> getPostById(@RequestParam("postId")String postId) throws SchoolCircleException {
        return Result.result(postService.getPostById(postId));
    }

    @RequestMapping("/post/reply")
    @ResponseBody
    public Result<?> getReplyByRootPostId(@RequestParam("postId")String postId) throws SchoolCircleException {
        return Result.result(postService.getReplyByRootPostId(postId));
    }

    @RequestMapping("/parentPost/reply")
    @ResponseBody
    public Result<?> getReplyByParentPostId(@RequestParam("postId")String postId) throws SchoolCircleException {
        return Result.result(postService.getReplyByParentPostId(postId));
    }

    @RequestMapping("/posts")
    @ResponseBody
    public Result<?> getPostListByType(@RequestParam("type")String postType) throws SchoolCircleException {
        return Result.result(postService.getPostListByType(postType));
    }
}
