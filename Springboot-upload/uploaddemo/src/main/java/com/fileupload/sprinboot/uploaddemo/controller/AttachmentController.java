package com.fileupload.sprinboot.uploaddemo.controller;


import com.fileupload.sprinboot.uploaddemo.entity.Attachment;
import com.fileupload.sprinboot.uploaddemo.model.ResponseData;
import com.fileupload.sprinboot.uploaddemo.repository.AttachmentRepository;
import com.fileupload.sprinboot.uploaddemo.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AttachmentRepository attachmentRepository;
    private Attachment attachment;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String downloadURL = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download").path(attachment.getId()).toUriString();

        return new ResponseData(attachment.getFileName(), downloadURL, file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) throws Exception {

        Attachment attachment = attachmentService.getAttachment(fileId);

        if (attachment == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                            + attachment.getFileName() + "\"")
                    .body(attachment.getData());
        } catch (Exception exception) {
            throw exception;
        }
    }
}
