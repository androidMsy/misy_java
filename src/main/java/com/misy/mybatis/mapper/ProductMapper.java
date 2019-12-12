package com.misy.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.misy.mybatis.entity.ProductEntity;
import com.misy.mybatis.entity.UserAccount;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends BaseMapper<ProductEntity> {
    List<ProductEntity> getAllProduct(String userId, int productType);
    List qyProductAndUser(String userId, Integer productType);
}
