package com.misy.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.misy.mybatis.entity.ProductEntity;
import com.misy.mybatis.entity.UserAccount;
import com.misy.mybatis.mapper.ProductMapper;
import com.misy.mybatis.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {
    @Override
    public List<ProductEntity> getAllProduct(String userId, int productType) {
        return getBaseMapper().getAllProduct(userId, productType);
    }
    @Override
    public List qyProductAndUser(String userId, Integer productType) {
        return getBaseMapper().qyProductAndUser(userId, productType);
    }

    @Override
    public void updataIsLike(String productId, Integer isLike) {
        LambdaQueryWrapper<ProductEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductEntity::getProductId, productId);
        ProductEntity productEntity = getOne(wrapper);
        productEntity.setIsLike(isLike);
        saveOrUpdate(productEntity, wrapper);
    }
}
