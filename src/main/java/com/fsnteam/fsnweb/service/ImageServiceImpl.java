package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.ImageData;
import com.fsnteam.fsnweb.util.GenerateUUID;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageDataService imageDataService;

    @Override
    public String describeImage(MultipartFile file) {
        //获取系统本地图片仓库路径
        //linux:/home/ubuntu/ImageStorage/
        //windows:D:\Project\JavaProject\ChatGPTBehind\ImageStorage\
        String osPath = "D:\\Project\\JavaProject\\ChatGPTBehind\\ImageStorage\\";
        //获取当前登录用户名
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //为每个用户单独创建文件夹
        osPath = osPath.concat(userId);
        Path userDirPath = Paths.get(osPath);
        if (!Files.exists(userDirPath)) {
            try {
                Files.createDirectories(userDirPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取原图片名及扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一标识符
        GenerateUUID generateUUID = new GenerateUUID();
        String uuid = generateUUID.generateUUID()+extension;
        Path filePath = Paths.get(osPath, uuid);

        // Check if the file already exists

        while (Files.exists(filePath)) {
            // If the file exists, generate a new UUID and create a new file path
            uuid = generateUUID.generateUUID()+extension;
            filePath = Paths.get(osPath, uuid);
        }
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建http请求，请求python算法接口
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://192.168.199.88:5000/upload/");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
            builder.addBinaryBody(
                    "images",
                    file.getBytes(),
                    ContentType.APPLICATION_OCTET_STREAM,
                    file.getOriginalFilename() // File name
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        String description = "";
        try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);
            // 解析 JSON
            JSONObject jsonObject = new JSONObject(responseString);

            // Get the key of the first pair
            String firstKey = jsonObject.keys().next();

            // Get the value of the first pair
            description = jsonObject.getString(firstKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String seachPath = osPath+"/"+uuid;
        //添加历史记录
        ImageData imageData = new ImageData();
        imageData.setId(userId);
        imageData.setDescription(description);
        imageData.setCreateTime(new Date());
        imageData.setFilePath(seachPath);
        imageDataService.save(imageData);
        return description;
    }
}
