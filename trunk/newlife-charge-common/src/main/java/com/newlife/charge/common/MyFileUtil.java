package com.newlife.charge.common;


import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(MyFileUtil.class);

    /**
     * 判断是否是图片文件后缀名
     *
     * @param filename
     * @return
     */
    public static boolean isImgExt(String filename) {
        String[] exts = {"BMP", "JPG", "JPEG", "PNG", "GIF"};
        String ext = getFileExt(filename).toUpperCase();
        return StringUtil.strInArray(exts, ext);
    }

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

    public static NewImage createNewFile(String ext, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = sdf.format(new Date());
        return createNewFile(dir, ext, request);
    }

    public static NewImage createNewFile(String dir, String ext, HttpServletRequest request) {
        //文件名称
        String imgName = System.currentTimeMillis() + String.valueOf((int) ((Math.random() * 9 + 1) * 100000)) + ext;
        //实际路径
        String imgPath = request.getSession().getServletContext()
                .getRealPath(getFileDirPath(dir))
                + File.separator + imgName;
        //访问路径
        String url = request.getContextPath() + "/" + getFileDirPath(dir)
                + "/" + imgName;

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

    public static String getFileDirPath(String dir) {
        return "upload/file/" + dir;
    }

//    public static String getFname(String fpath) {
//        if (fpath == null || "".equals(fpath)) return "";
//        String ss = fpath.replace("\\", "/");
//        String[] arr = ss.split("/");
//        return arr[arr.length - 1];
//    }

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

            commpressPicCycle(minFile, 400 * 1024, 0.8);

            logger.debug("created file :{}", "commpressPicCycle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
    private static void commpressPicCycle(File minFile, long desFileSize,
                                          double accuracy) throws Exception {
        long minFileSize = minFile.length();
        // 2、判断大小，如果小于 desFileSize kb，不压缩；如果大于等于desFileSize kb，压缩
        double scale = 0.9;
        if (minFileSize <= desFileSize || (minFileSize - desFileSize) <= (100 * 1024)) {
            //在100啊kb以内不处理
            return;
        } else {
            //1M 倍数
            if (minFileSize / (1024 * 1024) == 1) {
                scale = 0.8;
            } else if (minFileSize / (1024 * 1024) == 2) {
                scale = 0.75;
            } else if (minFileSize / (1024 * 1024) == 3) {
                scale = 0.7;
            } else if (minFileSize / (1024 * 1024) == 4) {
                scale = 0.6;
            } else if (minFileSize / (1024 * 1024) == 5) {
                scale = 0.5;
            } else if (minFileSize / (1024 * 1024) >= 6) {
                scale = 0.4;
            } else if (minFileSize > 0.8 * (1024 * 1024)) {
                scale = 0.8;
            }
//            scale = (desFileSize * 1024 * 1.0) / (minFileSize * 1.0);
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

    /**
     * 远程读取image转换为Base64字符串
     *
     * @param imgUrl
     * @return
     */
    public static String ImageBase64(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();

            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return new BASE64Encoder().encode(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    /**
     * Base64图片储存
     *
     * @param
     * @return
     */
    public static String uploadBase64(String imgBase64, HttpServletRequest request) {
//        System.out.print("uploadBase64:开始上传图片:"+imgBase64);
        NewImage newFile = null;
        try {
            //去掉头部 data:image/png;base64,
            int i1 = imgBase64.indexOf("base64,");
            String replaceData = imgBase64.substring(0, i1);
            imgBase64 = imgBase64.replace(replaceData, "").replace("base64,", "");

            BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgBase64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }

            //生成jpeg图片
            newFile = createNewFile(".png", request);

            //上传图片
            OutputStream out = new FileOutputStream(newFile.getPath());
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("传单个base64位的图片:" + imgBase64);
        }

        return newFile.getUrl();
    }

    /**
     * Base64图片储存
     *
     * @param
     * @return
     */
    public static String uploadBase64Rotate(String imgBase64, HttpServletRequest request) {
        NewImage newFile = null;
        try {
            //去掉头部 data:image/png;base64,
            int i1 = imgBase64.indexOf("base64,");
            String replaceData = imgBase64.substring(0, i1);
            imgBase64 = imgBase64.replace(replaceData, "").replace("base64,", "");

            BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgBase64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }

            //生成jpeg图片
            newFile = createNewFile(".png", request);

            //上传图片
            OutputStream out = new FileOutputStream(newFile.getPath());
            out.write(b);
            out.flush();
            out.close();

            File rotateFile = new File(newFile.getPath());
            Thumbnails.of(rotateFile).scale(0.8).rotate(-90).toFile(newFile.getPath());
            Files.copy(rotateFile.toPath(), rotateFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("传单个base64位的图片:" + imgBase64);
        }

        return newFile.getUrl();
    }

    /**
     * @Description: 文件输出流转换
     * @Author: Linzq
     * @CreateDate:  2018/10/9 0009 14:30
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            logger.error("Error at inputStreamToFile ",e);
        }
    }


}
