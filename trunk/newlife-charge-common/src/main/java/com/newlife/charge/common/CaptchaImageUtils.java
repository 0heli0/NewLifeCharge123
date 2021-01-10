package com.newlife.charge.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * base64 图形 验证码
 * <p>
 * Created by lincz on 2018/7/11 0011.
 */
public class CaptchaImageUtils {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaImageUtils.class);


    public static String NAME_SPACE = "captcha_code:";

    private static int w = 70;
    private static int h = 26;

    public static  String createImage(String sessionId){
        return createImage(null,null,sessionId);
    }

    public static String createImage(String width,String height,String sessionId) {
        try {
            //得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
            if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height)) {
                w = NumberUtils.toInt(width);
                h = NumberUtils.toInt(height);
            }

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();

            //生成背景
            createBackground(g);

            //生成字符
            String s = createCharacter(g);

            StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
            String key = new StringBuffer(NAME_SPACE).append(sessionId).toString();//存到redis
            redisTemplate.opsForValue().set(key, s, 1, TimeUnit.HOURS);//有效期1小时

            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
            ImageIO.write(image, "png", baos);//写入流中
            byte[] bytes = baos.toByteArray();//转换成字节
            BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n

            png_base64 = "data:image/jpeg;base64," + png_base64;
            return png_base64;
        } catch (Exception e) {
            logger.error("create image exception",e);
        }

        return "";
    }

    private static Color getRandColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

    private static void createBackground(Graphics g) {
        // 填充背景
        g.setColor(getRandColor(220, 250));
        g.fillRect(0, 0, w, h);
        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            g.setColor(getRandColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            g.drawLine(x, y, x1, y1);
        }
    }

    private static String createCharacter(Graphics g) {
        char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
                'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};
        String[] fontTypes = {"Arial", "Arial Black", "AvantGarde Bk BT", "Calibri"};
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
            g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
            g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, 26));
            g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
//			g.drawString(r, i*w/4, h-5);
            s.append(r);
        }
        return s.toString();
    }

    /**
     * 根据sessoinId，校验 验证码  是否正确
     * @param sessionId
     * @return
     */
    public static String getValidateCode(String sessionId) {
        String key = new StringBuffer(NAME_SPACE).append(sessionId).toString();
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return redisTemplate.opsForValue().get(key);
    }
}
