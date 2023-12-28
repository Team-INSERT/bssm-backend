package com.insert.ogbsm.service.image.implement;

import com.insert.ogbsm.infra.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageImplement {
    private final S3Service s3Service;

    public String append(MultipartFile file) {
        return s3Service.uploadImage(file);
    }
}
