package com.insert.ogbsm.service.image.business;

import com.insert.ogbsm.infra.s3.service.S3Service;
import com.insert.ogbsm.service.image.implement.ImageImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageDefBusiness {
    private final ImageImplement imageImplement;

    public String uploadImage(MultipartFile file) {
        return imageImplement.append(file);
    }
}
