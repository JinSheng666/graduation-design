package com.jinsheng.interceptor;

import com.alibaba.fastjson.JSON;
import com.jinsheng.common.Code;
import com.jinsheng.common.Result;
import com.jinsheng.utils.GraduationDesignConstants;
import com.jinsheng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 自定义拦截器


@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {

    //redis
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TokenUtils jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = null;
//
        String headerToken = request.getHeader(GraduationDesignConstants.HEADER_TOKEN_NAME);

        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            //遍历cookies拿到token密钥
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(GraduationDesignConstants.HEADER_TOKEN_NAME)) { // 假设 Token 对应的 Cookie 名称是 "Token"
                    token = cookie.getValue();
                    break;
                }

            }
        }else if (headerToken != null){
            token = headerToken;
        }

        String requestURI = request.getRequestURI();
        log.debug("Request URI: {}", requestURI);
        log.debug(request.getRequestURL()+"==>待验证："+token);
        log.debug(request.getRequestURI()+"==>待验证："+token);

        try {
            //在redis中存在token就放行
            redisTemplate.hasKey(token);
            jwtUtil.getCurrentUser(token);
            log.debug(request.getRequestURI()+"：验证成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug(request.getRequestURI()+"：禁止访问");
        response.setContentType("application/json;charset=utf-8");
        Result result = Result.result(Code.CODE_ERR_BUSINESS,false,"令牌无效，请重新登录!",null);
        response.getWriter().write(JSON.toJSONString(result));
        return false;

    }
}
