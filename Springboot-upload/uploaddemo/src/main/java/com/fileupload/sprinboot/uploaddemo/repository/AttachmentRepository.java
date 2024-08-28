package com.fileupload.sprinboot.uploaddemo.repository;

import com.fileupload.sprinboot.uploaddemo.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {


}
