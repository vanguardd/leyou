package com.leyou.search.client;

import com.leyou.LySearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryCategory() {
        List<String> categoryNames = categoryClient.queryNamesByIds(Arrays.asList(1L, 2L, 3L));
        categoryNames.forEach(System.out::println);
    }

}