package com.insert.ogbsm.service.image;

import com.insert.ogbsm.infra.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageDefService {
    private final S3Service s3Service;

    public String uploadImage(MultipartFile file) {
        return s3Service.uploadImage(file);
    }
}
