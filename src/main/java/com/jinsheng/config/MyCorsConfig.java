package com.jinsheng.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
* 配置跨域过滤器
* */

@Configuration
public class MyCorsConfig {

    @Value("${allow-cross-domain}")
    private String AllowCrossDomain;

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(AllowCrossDomain);  //允许跨域访问的资源路径，前端服务器的资源路径
        configuration.setAllowCredentials(true);  //是否允许cookie
        configuration.addAllowedMethod("*");  //“*”任何访问的方法都允许

        configuration.addAllowedHeader("*");  //允许的头信息

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);  //"/**" 拦截所有

        //返回新的CorsFilter
        return new CorsFilter(configurationSource);
    }


}
