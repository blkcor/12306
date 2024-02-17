package com.github.blkcor.config;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class KaptchaNoBackground extends Configurable implements BackgroundProducer {

    public KaptchaNoBackground() {
    }

    @Override
    public BufferedImage addBackground(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fill(new Rectangle2D.Double(0.0D, 0.0D, width, height));
        graphics.drawImage(bufferedImage, 0, 0, null);
        return image;
    }
}
