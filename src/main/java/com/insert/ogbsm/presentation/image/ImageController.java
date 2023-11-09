package com.insert.ogbsm.presentation.image;

import com.insert.ogbsm.service.image.business.ImageDefBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageDefBusiness imageDefBusiness;

    @PostMapping("/save")
    public String create(@RequestPart("image") MultipartFile multipartFile) {
        return imageDefBusiness.uploadImage(multipartFile);
    }
}
