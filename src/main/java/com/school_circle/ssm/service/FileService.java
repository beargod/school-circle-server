package com.school_circle.ssm.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * Created by BigGod on 2017-04-25.
 */
public interface FileService {
    void uploadFile(MultipartFile file);

    void uploadFiles(MultipartFile[] files);

    Collection<MultipartFile> getFileByRelationId(long id);
}
