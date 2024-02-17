package com.github.blkcor.config;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class KaptchaWaterRipple extends Configurable implements GimpyEngine {
    public KaptchaWaterRipple(){}
    @Override
    public BufferedImage getDistortedImage(BufferedImage bufferedImage) {
        NoiseProducer noiseProducer = this.getConfig().getNoiseImpl();
        BufferedImage distortedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 2);
        Graphics2D graph = (Graphics2D) distortedImage.getGraphics();
        Random random = new Random();
        RippleFilter rippleFilter = new RippleFilter();
        rippleFilter.setXAmplitude(7.6F);
        rippleFilter.setYAmplitude(random.nextFloat() + 1.0F);
        rippleFilter.setEdgeAction(1);
        BufferedImage effectImage = rippleFilter.filter(bufferedImage, null);
        graph.drawImage(effectImage, 0, 0, null, null);
        graph.dispose();
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.1F, 0.25F, 0.25F);
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.25F, 0.5F, 0.9F);
        return distortedImage;
    }
}
