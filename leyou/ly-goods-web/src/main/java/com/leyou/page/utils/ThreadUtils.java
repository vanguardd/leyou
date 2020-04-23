package com.leyou.page.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Title: 线程池工具类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/23
 */
public class ThreadUtils {

    /**
     * 创建线程池
     * @param corePoolSize
     * @param maximumPoolSize
     * @param threadName
     * @return java.util.concurrent.ExecutorService
     * @author vanguard
     * @date 20/4/23 20:14
     */
    public static ExecutorService buildThreadTool(int corePoolSize, int maximumPoolSize, String threadName) {

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadName).build();

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), threadFactory);
    }
}
