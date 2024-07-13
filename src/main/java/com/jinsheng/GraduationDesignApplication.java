package com.jinsheng;

import com.jinsheng.utils.VideoOrImageUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//redis缓存注解第一步：开启redis缓存注解
@EnableCaching
//mapper接口扫描器，指明mapper接口所在包，然后就会自动为mapper接口创建代理对象并加入到IOC容器
@MapperScan(basePackages = "com.jinsheng.mapper")
@SpringBootApplication
public class GraduationDesignApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignApplication.class, args);
    }

    @Autowired
    private VideoOrImageUtil videoOrImageUtil;

    //初始化视频以及图片的存储位置
    @Override
    public void run(String... args) throws Exception {
        videoOrImageUtil.init();
    }

}
