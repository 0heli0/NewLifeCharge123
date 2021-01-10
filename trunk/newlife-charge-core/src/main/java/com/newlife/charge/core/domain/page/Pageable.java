/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.newlife.charge.core.domain.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 分页类
 *
 * @author zhengyou
 */
@Component
@ApiModel
public class Pageable {

    @ApiModelProperty(value = "页容量",example = "20")
    public static int pageSize = 10;

    @ApiModelProperty(value = "页码",example = "1")
    public int pageNo = 1; // 当前页码


    public Pageable() {

    }

    /**
     * 构造方法
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     */
    public Pageable(int pageNo, int pageSize) {
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }


    /**
     * 获取当前页码
     *
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页码
     *
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取页面大小
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小
     *
     * @param pageSize
     */

    @Value("${page.pageSize}")
    public void setPageSize(int pageSize) {
        Pageable.pageSize = pageSize <= 0 ? Pageable.pageSize : pageSize;
    }


}
