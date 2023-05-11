package ru.strict.controltime.view.common;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@UtilityClass
public class ImageUtil {

    public ImageIcon resizeImage(String pathImage, int width, int height) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(pathImage));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        var cm = bufferedImage.getColorModel();
        var raster = cm.createCompatibleWritableRaster(width, height);
        var isRasterPremultiplied = cm.isAlphaPremultiplied();
        var target = new BufferedImage(cm, raster, isRasterPremultiplied, null);
        var g2 = target.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        var scalex = (double)target.getWidth() / (double)bufferedImage.getWidth();
        var scaley = (double)target.getHeight() / (double)bufferedImage.getHeight();
        var xform = AffineTransform.getScaleInstance(scalex, scaley);
        g2.drawRenderedImage(bufferedImage, xform);
        g2.dispose();

        return new ImageIcon(target);
    }
}
