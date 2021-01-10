package com.newlife.charge.common.ms.vo;

import java.util.List;

/**
 * 多个文件上传 File 解析类
 *
 * @Author lincz on 2019/1/15 0015.
 */
public class FilesModel extends BaseModel {

    private List<FileInfo> data;

    public List<FileInfo> getData() {
        return data;
    }

    public void setData(List<FileInfo> data) {
        this.data = data;
    }
}
