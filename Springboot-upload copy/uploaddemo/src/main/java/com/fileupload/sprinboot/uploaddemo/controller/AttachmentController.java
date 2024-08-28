package com.fileupload.sprinboot.uploaddemo.controller;


import com.fileupload.sprinboot.uploaddemo.entity.Attachment;
import com.fileupload.sprinboot.uploaddemo.model.ResponseData;
import com.fileupload.sprinboot.uploaddemo.repository.AttachmentRepository;
import com.fileupload.sprinboot.uploaddemo.service.AttachmentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentRepository arepo;
    private Attachment attachment;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        String downloadURL = "";
          attachment = attachmentService.saveAttachment(file);
          downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download").path(attachment.getId()).toUriString();
 
          return new ResponseData(attachment.getFileName(), downloadURL, file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + attachment.getFileName() + "\"").body((Resource) new ByteArrayResource(attachment.getData()));


    }


}
