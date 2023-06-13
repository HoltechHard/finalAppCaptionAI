package com.fsnteam.fsnweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public interface ImageService {

//    根据上传图片进行描述
    String describeImage(MultipartFile file);
}
