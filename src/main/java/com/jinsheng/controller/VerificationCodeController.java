package com.jinsheng.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码接口")
@RestController
@RequestMapping("/captcha")
public class VerificationCodeController {
    //指定注入资源
    @Resource(name = "captchaProducer")
    private Producer producer;

    //redis
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 验证码
     * */
    @ApiOperation("验证码")
    @GetMapping("/captchaImage")
    public void captchaImage(HttpServletResponse response){

        ServletOutputStream out = null;

        try{
            //生成验证码文本
            String text = producer.createText();
            //生成验证码图片 --BufferedImage对象代表生成的验证码图片在内存中
            BufferedImage image = producer.createImage(text);
            //将验证码保存到redis中,30分钟后过期
            redisTemplate.opsForValue().set(text,"",60, TimeUnit.SECONDS);

            /**
             * 将验证码响应给前端
             * */
            //设置响应正文类型image/jpeg
            response.setContentType("image/jpeg");
            //将验证码图片响应给前端
            out = response.getOutputStream();
            ImageIO.write(image,"jpg",out);//使用响应对象的字节输出流写入验证码图片
            //刷新
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭字节输出流
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
