package com.github.blkcor.controller.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

        @GetMapping("/image-code/{imageCodeToken}")
        public void imageCode(@PathVariable(value = "imageCodeToken") String imageCodeToken, HttpServletResponse response) throws IOException {
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            try {
                //生成验证码
                String text = defaultKaptcha.createText();
                //将验证码放入redis，等待后续的验证
                redisTemplate.opsForValue().set(imageCodeToken, text, 300, TimeUnit.SECONDS);
                //生成验证码图片
                BufferedImage image = defaultKaptcha.createImage(text);
                ImageIO.write(image, "jpg", jpegOutputStream);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
            byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            response.setContentLength(captchaChallengeAsJpeg.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(captchaChallengeAsJpeg);
            outputStream.flush();
            outputStream.close();
        }
}
