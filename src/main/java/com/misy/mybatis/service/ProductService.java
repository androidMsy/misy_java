package com.misy.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.misy.mybatis.entity.ProductEntity;
import com.misy.mybatis.entity.UserAccount;

import java.util.List;
import java.util.Map;


public interface ProductService extends IService<ProductEntity> {
    List<ProductEntity> getAllProduct(String userId, int productType);
    List qyProductAndUser(String userId, Integer productType);
    void updataIsLike(String productId, Integer isLike);
}
