package com.newlife.charge.common.FileUtil;


import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传工具类
 *
 * Created by lincz on 2017/12/11 0011 10:35.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getFileExt(String filename) {
        if (filename != null && !"".equals(filename) && filename.indexOf(".") > -1) {
            return filename.split("\\.")[1];
        }
        return "";
    }

    /**
     * 获取文件名
     *
     * @param filename
     * @return
     */
    public static String getFileName(String filename) {
        if (filename != null && !"".equals(filename) && filename.indexOf(".") > -1) {
            return filename.split("\\.")[0];
        }
        return "";
    }

    //上传目录
    public static String getFileDirPath(String dir) {
        return "upload/file/" + dir;
    }

    public static NewImage createNewFile(String ext, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = sdf.format(new Date());
        return createNewFile(dir, ext, request);
    }

    public static NewImage createNewFile(String dir, String ext, HttpServletRequest request) {
        //文件名称
        String imgName = System.currentTimeMillis() + String.valueOf((int) ((Math.random() * 9 + 1) * 100000)) + ext;
        //TODO:文件实际保存路径（项目在容器中的实际发布运行的根路径）
        String imgPath = request.getSession().getServletContext()
                .getRealPath(getFileDirPath(dir))
                + File.separator + imgName;
        //访问路径
        String url = request.getContextPath() + File.separator + getFileDirPath(dir)
                + File.separator + imgName;

        File file = new File(imgPath);
        if (!file.getParentFile().isDirectory()) {
            file.getParentFile().delete();
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        NewImage newImage = new NewImage(imgPath, url, imgName, ext);
        return newImage;
    }

    /**
     * 处理成缩略图
     *
     * @param targetFile
     * @param newImage
     */
    public static void createMinPic(MultipartFile targetFile, NewImage newImage) {
        //缩略图路径
        String minPath = newImage.getPath().replace("file", "file_min");
        try {
            File minFile = new File(minPath);
            if (!minFile.getParentFile().isDirectory()) {
                logger.debug("created file :{}", "createDirectory");
                minFile.getParentFile().mkdirs();
            }

            targetFile.transferTo(minFile);

            logger.debug("created file :{}", "createMinPic");

            commpressPicCycle(minFile, 200, 0.8);

            logger.debug("created file :{}", "commpressPicCycle");
        } catch (Exception e) {
            logger.error("处理成缩略图异常",e);
        }
    }

    //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
    private static void commpressPicCycle(File minFile, long desFileSize,
                                          double accuracy) throws Exception {
        long minFileSize = minFile.length();
        // 2、判断大小，如果小于 desFileSize kb，不压缩；如果大于等于desFileSize kb，压缩
        double scale = 0.8;
        if (minFileSize <= desFileSize * 1024) {
            return;
        } else {
            scale = (desFileSize * 1024 * 1.0) / (minFileSize * 1.0);
        }

        // 计算宽高
        BufferedImage bim = ImageIO.read(minFile);
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith).multiply(new BigDecimal(scale)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(new BigDecimal(scale)).intValue();

        Thumbnails.of(minFile).size(desWidth, desHeight).outputQuality(accuracy).toFile(minFile);

        commpressPicCycle(minFile, desFileSize, accuracy);
    }


    /**
     * 校验一个文件是否是图片文件
     *
     * @param file : 文件
     * @return 如果是图片文件，则返回true，如果不是图片文件则返回false
     */
    public static boolean isPic(MultipartFile file) {
        try {
            Image imageSrc = ImageIO.read(file.getInputStream());
            // 得到源图宽
            imageSrc.getWidth(null);

            return true;
        } catch (Exception e) {
            logger.error("非图片文件：",e);
            return false;
        }
    }

}
