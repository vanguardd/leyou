package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/12/17
 */
@RestController
@RequestMapping("spu")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Spu>> getSpuByPage(
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                            @RequestParam(value = "saleable", required = false) Boolean saleable,
                                            @RequestParam(value = "key", required = false) String key) {
        PageResult<Spu> spuPageResult = goodsService.getSpuByPage(page, rows, saleable, key);
        return ResponseEntity.ok(spuPageResult);
    }
}
