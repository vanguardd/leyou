package com.leyou.page.controller;

import com.leyou.page.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.soap.Addressing;
import java.util.Map;

/**
 * @Title: 商品详情
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/22
 */
@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 跳转到商品详情页
     * @param model
     * @param id
     * @return java.lang.String
     * @author vanguard
     * @date 20/4/22 19:30
     */
    @GetMapping("{id}.html")
    public String toItemPage(Model model, @PathVariable("id") Long id) {
        Map<String, Object> modelMap = goodsService.loadModel(id);
        model.addAllAttributes(modelMap);
        return "item";
    }
}
