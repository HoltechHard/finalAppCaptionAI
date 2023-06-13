package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.entity.ImageData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author StupidBear
 * @since 2023-06-10
 */
public interface ImageDataService extends IService<ImageData> {

    //根据url查询图片
    Map<String, String> getImageByUrl(String url);
}
