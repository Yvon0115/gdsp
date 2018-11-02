package com.gdsp.dev.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Random的相关工具类
 * @author Tim
 * @version 1.0 2015年2月5日
 * @since 1.6
 */
public final class RandomUtils {

    // 验证码字符个数
    private static final int    CHAR_NUM = 4;
    // 随机字符数组
    private static final char[] CHAR_SEQ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static Object[] getRandomImage() {
        // 指定图形验证码图片的大小
        int width = 80, height = 25;
        // 生成一张新图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 在图片中绘制内容
        Font myFont = new Font("Arial", Font.BOLD, 16);
        Graphics g = image.getGraphics();
        g.setColor(getRandomColor(200, 250));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(102, 102, 102));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(myFont);
        // 随机生成线条，让图片看起来更加杂乱
        g.setColor(getRandomColor(160, 200));
        Random random = new Random();
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);// 起点的x坐标
            int y = random.nextInt(height - 1);// 起点的y坐标
            int x1 = random.nextInt(6) + 1;// x轴偏移量
            int y1 = random.nextInt(12) + 1;// y轴偏移量
            g.drawLine(x, y, x + x1, y + y1);
        }
        // 随机生成线条，让图片看起来更加杂乱
        for (int i = 0; i < 70; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(12) + 1;
            int y1 = random.nextInt(6) + 1;
            g.drawLine(x, y, x - x1, y - y1);
        }
        // 该变量用来保存系统生成的随机字符串
        StringBuilder sRand = new StringBuilder(CHAR_NUM);
        for (int i = 0; i < CHAR_NUM; i++) {
            // 取得一个随机字符
            String tmp = getRandomChar();
            sRand.append(tmp);
            // 将系统生成的随机字符添加到图形验证码图片上
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random
                    .nextInt(110)));
            g.drawString(tmp, 15 * i + 10, 20);
        }
        Object[] result = new Object[2];
        g.dispose();
        result[0] = image;
        result[1] = sRand;
        // 输出图形验证码图片
        return result;
    }

    // 随机生成一个字符
    private static String getRandomChar() {
        Random random = new Random();
        int index = random.nextInt(CHAR_SEQ.length);
        return String.valueOf(CHAR_SEQ[index]);
    }

    // 生成随机颜色
    private static Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
