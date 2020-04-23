package com.leyou.page.service;

import com.leyou.page.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Title: 详情页面静态化
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/23
 */
@Slf4j
@Service
public class GoodsHtmlService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${ly.item.html.path}")
    private String itemHtmlPath;

    /**
     * 线程名称
     */
    public static String THREAD_NAME = "create-html-thread-%d";

    /**
     * 线程大小
     */
    public static int THREAD_SIZE = 10;

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    public void createHtml(Long spuId) {

        // 创建输出流
        File file = new File(itemHtmlPath + spuId + ".html");

        try(PrintWriter writer = new PrintWriter(file);) {
            // 获取页面数据
            Map<String, Object> spuMap = this.goodsService.loadModel(spuId);
            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(spuMap);

            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            log.error("页面静态化出错：{}，"+ e, spuId);
        }
    }

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    public void asyncExcute(Long spuId) {
        ExecutorService executorService = ThreadUtils.buildThreadTool(THREAD_SIZE, THREAD_SIZE, THREAD_NAME);
        executorService.submit(() -> createHtml(spuId));
    }
}
