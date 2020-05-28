package com.leyou.search.client;

import com.leyou.LySearchApplication;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private IndexService indexService;

    @Test
    public void createIndex(){
        // 创建索引
        this.template.createIndex(Goods.class);
        // 配置映射
        this.template.putMapping(Goods.class);
        int page = 1;
        int rows = 100;

        do {
            // 分批查询spuBo
            PageResult<Spu> pageResult = goodsClient.getSpuByPage(page, rows, true, null);
            // 遍历spubo集合转化为List<Goods>
            List<Goods> goodsList = pageResult.getItems().stream().map(spu -> indexService.buildGoods(spu)).collect(Collectors.toList());
            goodsRepository.saveAll(goodsList);

            // 获取当前页的数据条数，如果是最后一页，没有100条
            rows = pageResult.getItems().size();
            // 每次循环页码加1
            page++;
        } while (rows == 100);
    }


}