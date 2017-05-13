package com.school_circle.ssm.service.impl;

import com.school_circle.ssm.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * Created by BigGod on 2017-04-25.
 */
public class FileServiceImpl implements FileService{
    @Override
    public void uploadFile(MultipartFile file) {

    }

    @Override
    public void uploadFiles(MultipartFile[] files) {

    }

    @Override
    public Collection<MultipartFile> getFileByRelationId(long id) {
        return null;
    }
}
