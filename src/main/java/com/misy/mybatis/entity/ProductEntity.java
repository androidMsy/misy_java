package com.misy.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@TableName("product")
public class ProductEntity implements Serializable {
    private String userId;
    private String productId;
    private String productName;
    //0 1 2
    private int productType;
    private String content;
    private double price;
    private String coverImg;
    private String srcImgs;
    private int isLike;
    private String extend;
    @TableField(exist = false)
    private Map userAccount;
}
 