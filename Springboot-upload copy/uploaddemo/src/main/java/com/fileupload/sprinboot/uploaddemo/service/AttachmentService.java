package com.fileupload.sprinboot.uploaddemo.service;

import com.fileupload.sprinboot.uploaddemo.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    Attachment saveAttachment(MultipartFile file) throws Exception;
    

    Attachment getAttachment(String fileId) throws Exception;
}
