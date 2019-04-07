package com.itany_zshopDemo.service;

import com.itany_zshopDemo.pojo.ProductType;

import java.util.List;

public interface ProductTypeService {

    /**
     * 查找所有商品类型信息
     * @return
     */
    public List<ProductType> findAll();
}
