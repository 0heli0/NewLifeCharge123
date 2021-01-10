package com.newlife.charge.common.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.newlife.charge.common.pdf.vo.SignPdfIn;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * PDF 工具类
 * <p>
 * Created by lincz on 2018/7/3 0003.
 */
public class PdfUtils {

    /**
     * 创建 PDF
     */
    public static void createNoticePdf(SignPdfIn signPdfIn, HttpServletResponse response) {
        ByteArrayOutputStream bos = null;
        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);//PageSize.A4  A4的尺寸
            document.setMargins(50, 50, 100, 50);      //pdf的4个页边距
            Rectangle pageRect = document.getPageSize();

            // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
            // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。

            bos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, bos);

            //创建汉字字体
            BaseFont bfSong = BaseFont.createFont(signPdfIn.getFontPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //字体大小
            Font fontTitle = new Font(bfSong, 14, Font.NORMAL);
            Font fontContent = new Font(bfSong, 10, Font.NORMAL);
            Font timeContent = new Font(bfSong, 8, Font.NORMAL);

            // 3.打开文档
            document.open();

            // 4.添加一个内容段落
            Paragraph titlePara = new Paragraph(signPdfIn.getTitle(), fontTitle);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            document.add(titlePara);

            //添加新闻来源  //添加通知时间
            Paragraph noticeFromPara = new Paragraph("发布人：" + signPdfIn.getNoticeFrom() + "           " + signPdfIn.getNoticeTime(), timeContent);
            noticeFromPara.setAlignment(Element.ALIGN_RIGHT);
            noticeFromPara.setSpacingBefore(20);
            document.add(noticeFromPara);

            Paragraph contentPara = new Paragraph(signPdfIn.getContent(), fontContent);
            //段前间 隙
            contentPara.setSpacingBefore(15);
            contentPara.setSpacingAfter(15);
            contentPara.setAlignment(Element.ALIGN_LEFT);
            contentPara.setFirstLineIndent(20);//首行缩进
            document.add(contentPara);

            //图片1
            Image signImage = Image.getInstance(new URL(signPdfIn.getSignImageUrl()));
            signImage.setAlignment(Image.RIGHT);
            signImage.scalePercent(25);
            //将图片1添加到pdf文件中
            document.add(signImage);

            Paragraph studentInfo = new Paragraph();
            studentInfo.setAlignment(Element.ALIGN_RIGHT);

            Chunk studentNamePara = new Chunk("学生：" + signPdfIn.getStudentName(), timeContent);
            studentInfo.add(studentNamePara);
            studentInfo.add(Chunk.NEWLINE);

            Chunk signTimePara = new Chunk("回签时间：" + signPdfIn.getSignTime(), timeContent);
            studentInfo.add(signTimePara);

            document.add(studentInfo);

            // 5.关闭文档
            document.close();

            String fileName = signPdfIn.getGradeClassName() + signPdfIn.getStudentName() + ".pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            OutputStream out = response.getOutputStream();

            out.write(bos.toByteArray());
            out.flush();
            out.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据 模板创建pdf,以流的形式返回
     *
     * @param response
     * @param templatePath
     * @param fontPath
     * @param valueMap
     */
    public static void createTempaltePdf(HttpServletResponse response, String templatePath, String fontPath, Map<String, String> valueMap, String fileName) {
        PdfReader reader;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            //添加中文字体
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            form.addSubstitutionFont(bf);

            Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                form.setField(key, valueMap.get(key));
            }

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            OutputStream out = response.getOutputStream();

            out.write(bos.toByteArray());
            out.flush();
            out.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
