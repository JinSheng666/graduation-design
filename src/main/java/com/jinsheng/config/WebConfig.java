package com.jinsheng.config;

import com.jinsheng.interceptor.JwtValidateInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

// 拦截器 资源路径拦截


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtValidateInterceptor jwt;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /*拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        InterceptorRegistration interceptor = registry.addInterceptor(jwt);

        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/user/login");
        pathPatterns.add("/users/addUser");
        pathPatterns.add("/captcha/captchaImage");
        pathPatterns.add("/swagger-ui/**");
        pathPatterns.add("/swagger-resources/**");
        pathPatterns.add("/v3/**");
        pathPatterns.add("/forums/img-video-upload");
        pathPatterns.add("/users/user-img-upload");
        pathPatterns.add("/videos/video-upload");

        interceptor.addPathPatterns("/**") //对所有请求路径拦截
                .excludePathPatterns(pathPatterns);  //排除不拦截的路径
    }
}
