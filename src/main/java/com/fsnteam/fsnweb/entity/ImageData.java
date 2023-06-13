package com.fsnteam.fsnweb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author StupidBear
 * @since 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ImageData")
@ApiModel(value="ImageData对象", description="")
public class ImageData implements Serializable {

    @TableField("ID")
    private String id;

    @TableField("Create_time")
    private Date createTime;

    @TableField("description")
    private String description;

    @TableField("file_path")
    private String filePath;
}
