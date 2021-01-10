package com.newlife.charge.controller;

import com.newlife.charge.common.MyFileUtil;
import com.newlife.charge.common.NewImage;
import com.newlife.charge.common.ms.vo.FileInfo;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.service.ms.MsApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 后台上传文件，对接的统一文件上传服务
 * @Author: Linzq
 * @CreateDate: 2018/12/21 0021 10:50
 */
@Api(description = "上传文件")
@RestController
@RequestMapping("/api/minioRest")
public class MinioRestController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MinioRestController.class);

    @Value("${commonService.path}")
    private String commonServicePath;

    @Value("${commonService.picApiKey}")
    private String picApiKey;

    @Value("${commonService.picSecretKey}")
    private String picSecretKey;

    //上传空间
    @Value("${commonService.picMsApiKey}")
    private String picMsApiKey;

    @Value("${commonService.uploadFile}")
    private String uploadFile;

    @Value("${commonService.uploadFiles}")
    private String uploadFiles;

    @Autowired
    private MsApi msApi;

    //上传单个文件
    @ApiOperation(value = "上传单个文件", notes = "上传单个文件")
    @RequestMapping(value = "/uploadSingleFile", method = RequestMethod.POST)
    public Response uploadSingleFile(@RequestParam(value = "file") @ApiParam(required = true, name = "file") MultipartFile file) {
        try {
            //获取通用服务上传token
            String token = msApi.createAccessToken(picApiKey, picSecretKey);
            File f = null;
            InputStream ins = file.getInputStream();
            f = new File(file.getOriginalFilename());
            MyFileUtil.inputStreamToFile(ins, f);

            //调用上传文件接口
            FileInfo fileInfo = msApi.uploadFile(commonServicePath + uploadFile, picMsApiKey, f, token);

            //删除临时文件
            File del = new File(f.toURI());
            del.delete();
            return success(fileInfo).setMessage("上传成功");
        } catch (Exception e) {
            logger.error("单文件上传失败", e);
            return error("上传失败");
        }

    }


    //上传多个文件
    @ApiOperation(value = "批量上传文件", notes = "批量上传文件")
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public Response uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {

            //获取通用服务上传token
            String token = msApi.createAccessToken(picApiKey, picSecretKey);
            //转化File 文件
            List<File> fileList = new ArrayList<>();
            changeFileType(files, fileList);

            //调用上传文件接口
            List<FileInfo> fileInfoList = msApi.uploadFiles(commonServicePath + uploadFiles, fileList.toArray(new File[fileList.size()]), token, picMsApiKey);

            //删除临时文件
            for (File f : fileList) {
                File del = new File(f.toURI());
                del.delete();
            }
            return success(fileInfoList).setMessage("上传成功");

        } catch (Exception e) {
            logger.error("批量上传文件失败", e);
            return error("上传失败");
        }
    }

    /**
     * @param files
     * @param fileList
     */
    private void changeFileType(@RequestParam("files") MultipartFile[] files, List<File> fileList) {
        for (MultipartFile file : files) {
            File f = null;
            try {
                InputStream ins = file.getInputStream();
                f = new File(file.getOriginalFilename());
                MyFileUtil.inputStreamToFile(ins, f);
            } catch (Exception e) {
                logger.error("",e);

            }
            fileList.add(f);
        }

    }


    /**
     * 裁剪 上传图片
     *
     * @param file
     * @param x
     * @param y
     * @param w
     * @param h
     * @param show_w 是页面上显示图片的宽度大小
     * @return
     */
    @RequestMapping(value = "/cut/singleFile", method = RequestMethod.POST)
    public Response uploadCutSingleFile(@RequestParam(value = "file") MultipartFile file,
                                        @RequestParam(required = false, value = "x", defaultValue = "0") int x,
                                        @RequestParam(required = false, value = "y", defaultValue = "0") int y,
                                        @RequestParam(required = false, value = "w", defaultValue = "50") int w,
                                        @RequestParam(required = false, value = "h", defaultValue = "50") int h,
                                        @RequestParam(required = false, value = "show_w", defaultValue = "100") double show_w, HttpServletRequest request) {
        NewImage newImage;
        try {
            String originalFilename = file.getOriginalFilename();//原文件名
            String[] split = originalFilename.split("\\.");
            newImage = MyFileUtil.createNewFile("." + split[1], request);
            newImage.setSourceFileName(split[0]);

            File targetFile = new File(newImage.getPath());

            //缩放比例
            BufferedImage image = ImageIO.read(file.getInputStream());
            double scale = image.getWidth() / show_w;//show_w 当100 时计算比例，当1时就不处理
            Thumbnails.of(file.getInputStream()).sourceRegion((int) (x * scale), (int) (y * scale), (int) (w * scale), (int) (h * scale)).scale(1f).toFile(targetFile);

            logger.debug("created file :{}", newImage);
        } catch (Exception e) {
            logger.error("",e);
            throw new RuntimeException("上传失败");
        }
        return response(newImage).setMessage("上传成功");
    }
}
