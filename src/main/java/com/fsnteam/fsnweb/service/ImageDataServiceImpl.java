package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.ImageData;
import com.fsnteam.fsnweb.mapper.ImageDataMapper;
import com.fsnteam.fsnweb.service.ImageDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author StupidBear
 * @since 2023-06-10
 */
@Service
public class ImageDataServiceImpl extends ServiceImpl<ImageDataMapper, ImageData> implements ImageDataService {

    //根据url查询图片
    public Map<String, String> getImageByUrl(String url) {
        try {
            Path filePath = Paths.get(url);
            byte[] fileContent = Files.readAllBytes(filePath);
            String encodedContent = Base64.getEncoder().encodeToString(fileContent);
            String fileName = filePath.getFileName().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            Map<String, String> result = new HashMap<>();
            result.put("imageBytes", encodedContent);
            Map<String, String> extensionToMimeType = new HashMap<>();
            extensionToMimeType.put("jpg", "image/jpeg");
            extensionToMimeType.put("jpeg", "image/jpeg");
            extensionToMimeType.put("png", "image/png");
            String mimeType = extensionToMimeType.get(fileExtension);
            result.put("extension", mimeType);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

