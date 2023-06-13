package com.fsnteam.fsnweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fsnteam.fsnweb.entity.ImageData;
import com.fsnteam.fsnweb.entity.Users;
import com.fsnteam.fsnweb.service.ImageDataService;
import com.fsnteam.fsnweb.service.ImageService;
import com.fsnteam.fsnweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author StupidBear
 * @since 2023-06-10
 */
@RestController
    @RequestMapping("imageData")
public class ImageDataController {

    @Autowired
    ImageDataService imageDataService;

    @PostMapping("getHistory")
    public Result getHistory(@RequestBody Map params){
        int current = (int)params.get("current");
        int size = (int) params.get("size");
        LambdaQueryWrapper<ImageData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ImageData::getId,ImageData::getCreateTime,ImageData::getDescription,ImageData::getFilePath);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        queryWrapper.eq(ImageData::getId,userId);
        queryWrapper.orderByDesc(ImageData::getCreateTime);
        Page<ImageData> page = new Page<>(current,size);
        Page<ImageData> imageHistoryPage = imageDataService.page(page,queryWrapper);
        long total = imageHistoryPage.getTotal();
        List<ImageData> records = imageHistoryPage.getRecords();
        return Result.success().data("total",total).data("records",records);
    }

    @PostMapping("getImageByUrl")
    public Result getImageByUrl(@RequestBody Map params){
        String url = (String) params.get("url");
        Map<String, String> imageContent = imageDataService.getImageByUrl(url);
        String imageBytes = imageContent.get("imageBytes");
        String extension = imageContent.get("extension");
        return Result.success().data("imageContent",imageBytes).data("mimeType",extension);
    }
}
