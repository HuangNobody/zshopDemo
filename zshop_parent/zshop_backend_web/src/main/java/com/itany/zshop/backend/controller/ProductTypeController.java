package com.itany.zshop.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itany_zshopDemo.common.constant.PaginationConstant;
import com.itany_zshopDemo.pojo.ProductType;
import com.itany_zshopDemo.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/backend/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum, Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = PaginationConstant.PAGE_NUM;
        }
        //设置分页
        PageHelper.startPage(pageNum, PaginationConstant.PAGE_SIZE);

        //查找所有商品类型
        List<ProductType> productTypes = productTypeService.findAll();

        //model.addAllAttributes("types",types);
        //model.addAllAttributes("types",types); 这三句话不一样
        model.addAttribute("productTypes ",productTypes);

        //将查询的结果封装到PageInfo中
        PageInfo<ProductType> pageInfo = new PageInfo<>(productTypes);
//        pageInfo.getPageNum();
//        pageInfo.getPages();
//        pageInfo.getNextPage();
//        pageInfo.getPrePage();
//        pageInfo.getList();
        model.addAttribute("pageInfo",pageInfo);

        return "productTypeManager";
    }

}
