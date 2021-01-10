package com.newlife.charge.common.ms.vo;

/**
 * 单个文件上传 File 解析类
 *
 * @Author lincz on 2019/1/15 0015.
 */
public class FileModel extends BaseModel {

    private FileInfo data;

    public FileInfo getData() {
        return data;
    }

    public void setData(FileInfo data) {
        this.data = data;
    }
}
