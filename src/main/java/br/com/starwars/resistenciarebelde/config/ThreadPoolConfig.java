package br.com.starwars.resistenciarebelde.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService threadPool(@Value("${threadpool.corepoolsize}") final int corePoolSize,
                                      @Value("${threadpool.maximumPoolSize}")final int maximumPoolSize,
                                      @Value("${threadpool.keepAliveTimeInMinutes}")final int keepAliveTimeInMinutes){
        System.out.println("Corepool" + corePoolSize);
        final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executorService.setCorePoolSize(corePoolSize);
        executorService.setMaximumPoolSize(maximumPoolSize);
        executorService.setKeepAliveTime(keepAliveTimeInMinutes, TimeUnit.MINUTES);
        return executorService;
    }

    @Bean
    public TaskExecutor taskExecutor(@Value("${threadpool.corepoolsize}") final int corePoolSize,
                                     @Value("${threadpool.maximumPoolSize}")final int maximumPoolSize){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        return executor;
    }

    @Bean(name = "outroNome")
    public ExecutorService forkJoinPool(){
        return ForkJoinPool.commonPool();
    }

}
