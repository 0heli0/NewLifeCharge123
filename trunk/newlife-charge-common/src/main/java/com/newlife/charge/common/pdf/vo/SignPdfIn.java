package com.newlife.charge.common.pdf.vo;

/**
 * 回签通知的签名 生成 pdf 文件
 * <p>
 * Created by lincz on 2018/7/3 0003.
 */
public class SignPdfIn {

    private String title;

    private String content;

    private String noticeFrom;

    private String noticeTime;

    private String signImageUrl;

    private String studentName;

    private String signTime;

    private String fontPath;

    private String gradeClassName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoticeFrom() {
        return noticeFrom;
    }

    public void setNoticeFrom(String noticeFrom) {
        this.noticeFrom = noticeFrom;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public String getGradeClassName() {
        return gradeClassName;
    }

    public void setGradeClassName(String gradeClassName) {
        this.gradeClassName = gradeClassName;
    }
}
