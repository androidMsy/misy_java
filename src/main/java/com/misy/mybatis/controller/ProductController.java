package com.misy.mybatis.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.misy.mybatis.entity.ProductEntity;
import com.misy.mybatis.service.ProductService;
import com.misy.mybatis.utils.ResultUtil;
import com.misy.mybatis.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.misy.mybatis.contacts.LoginContacts.UNLOGIN_STATUS401;
import static com.misy.mybatis.utils.RequestUtils.*;
import static com.misy.mybatis.utils.UUIDUtils.getUUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 发布商品
     * @param productEntity
     * @return
     */
    @RequestMapping(value = "/issueProduct", method = RequestMethod.POST)
    public Object issueProduct(@RequestBody ProductEntity productEntity){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        //判断非空字段
        productEntity.setUserId(getUserId());
        productEntity.setProductId(getUUID());
        productService.save(productEntity);
        return ResultUtil.success.data(productEntity);
    }


    /**
     * 获取当前登录用户的商品
     * @param productType
     * @return
     */
    @RequestMapping(value = "/getProduct",method = RequestMethod.GET)
    public Object getMineProduct(@RequestParam(value = "productType", required = false) Integer productType){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        return ResultUtil.success.data(productService.getAllProduct(getUserId(), null == productType ? 0 : productType));
    }

    /**
     * 获取所有商品
     * @return
     */
    @RequestMapping(value = "/getAllProduct", method = RequestMethod.GET)
    public Object getAllProduct(){
        return ResultUtil.success.data(productService.getAllProduct(null, 0));
    }

    /**
     * 获取不同类型的商品
     * @param productType
     * @return
     */
    @RequestMapping(value = "/getProductFromType", method = RequestMethod.GET)
    public Object getProduct(@RequestParam("productType") int productType){
        return ResultUtil.success.data(productService.getAllProduct(null, productType));
    }

    /**
     * 修改商品
     * @param productEntity
     * @return
     */
    @RequestMapping(value = "/updataProduct", method = RequestMethod.PUT)
    public Object updataProduct(@RequestBody ProductEntity productEntity){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        if (TextUtils.isEmpty(productEntity.getProductId()))return ResultUtil.fail.msg("productId必传");
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<ProductEntity>().eq(ProductEntity::getProductId, productEntity.getProductId());
        return ResultUtil.success.msg(productService.update(productEntity, wrapper) ? "修改成功" : "修改失败");
    }

    /**
     * 删除商品
     * @param productId
     * @return
     */
    @RequestMapping(value = "/deleteProduct",method = RequestMethod.DELETE)
    public Object deleteProduct(@RequestParam("productId")String productId){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        if (TextUtils.isEmpty(productId))return ResultUtil.fail.msg("productId不能为空");
        return ResultUtil.success.msg(productService.remove(new LambdaQueryWrapper<ProductEntity>().eq(ProductEntity::getProductId, productId)) ? "删除成功": "删除失败") ;
    }

    @RequestMapping(value = "/qyMineProduct", method = RequestMethod.GET)
    public Object qyProduct(){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        return ResultUtil.success.data(productService.qyProductAndUser(getUserId(), 0));
    }
    @RequestMapping(value = "/qyAllProduct", method = RequestMethod.GET)
    public Object qyAllProduct(){
        return ResultUtil.success.data(productService.qyProductAndUser(null, 0));
    }
    @RequestMapping(value = "/qyMineProductFromType", method = RequestMethod.GET)
    public Object qyMineProductFromType(@RequestParam("productType") Integer productType){
        if (!tokenIsAuthentication())return ResultUtil.fail.status(401).msg(UNLOGIN_STATUS401);
        return ResultUtil.success.data(productService.qyProductAndUser(getUserId(), productType));
    }
    @RequestMapping(value = "/qyAllProductFromType", method = RequestMethod.GET)
    public Object qyAllProductFromType(@RequestParam("productType") Integer productType){
        return ResultUtil.success.data(productService.qyProductAndUser(null, productType));
    }
    @RequestMapping(value = "/likeProduct", method = RequestMethod.PUT)
    public Object likeProduct(@RequestParam("productId") String productId){
        productService.updataIsLike(productId, 1);
        return ResultUtil.success.msg("点赞成功");
    }
    @RequestMapping(value = "/unLikeProduct", method = RequestMethod.DELETE)
    public Object unLikeProduct(@RequestParam("productId") String productId){
        productService.updataIsLike(productId, 0);
        return ResultUtil.success.msg("取消点赞成功");
    }
}
