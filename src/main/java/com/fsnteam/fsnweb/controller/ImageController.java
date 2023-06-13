package com.fsnteam.fsnweb.controller;

import com.fsnteam.fsnweb.entity.ImageData;
import com.fsnteam.fsnweb.service.ImageService;
import com.fsnteam.fsnweb.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("Image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("describeImage")
    public Result describeImage(@RequestParam("file") MultipartFile file){
        // 检查文件大小
        if (file.getSize() > 10 * 1024 * 1024) { // 文件大小超过10MB
            return Result.error();
        }
        String description = imageService.describeImage(file);
        return Result.success().data("description",description);
    }
}
