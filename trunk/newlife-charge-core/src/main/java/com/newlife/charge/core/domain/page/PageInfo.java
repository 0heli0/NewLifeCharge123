/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.newlife.charge.core.domain.page;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * 
 * @author ThinkGem
 * @version 2013-7-2
 * @param <T>
 */
@Component
@ApiModel
public class PageInfo<T> {

	@ApiModelProperty(value = "页容量")
	private static int pageSize;

	@ApiModelProperty(value = "页码")
	protected int pageNo = 1; // 当前页码

	@ApiModelProperty(value = "总条数")
	protected long totalCount;// 总条数

	@ApiModelProperty(value = "列表数据")
	protected List<T> data = new ArrayList<>();

	public PageInfo() {

	}

	/**
	 * 构造方法
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            分页大小
	 */
	public PageInfo(int pageNo, int pageSize) {
		this(pageNo, pageSize, 0);
	}

	/**
	 * 构造方法
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            分页大小
	 * @param totalCount
	 *            数据条数
	 */
	public PageInfo(int pageNo, int pageSize, long totalCount) {
		this(pageNo, pageSize, totalCount, new ArrayList<T>());
	}

	/**
	 * 构造方法
	 * 
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            分页大小
	 * @param totalCount
	 *            数据条数
	 * @param data
	 *            本页数据对象列表
	 */
	public PageInfo(int pageNo, int pageSize, long totalCount, List<T> data) {
		this.setTotalCount(totalCount);
		this.setPageNo(pageNo);
		PageInfo.pageSize = pageSize;
		this.data = data;
	}

	/**
	 * 获取设置总数
	 * 
	 * @return
	 */
	public long getTotalCount() {
		return totalCount;
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
		PageInfo.pageSize = pageSize <= 0 ? PageInfo.pageSize : pageSize;
	}

	/**
	 * 获取本页数据对象列表
	 * 
	 * @return List<T>
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 设置本页数据对象列表
	 *
	 * @param data
	 */
	public PageInfo<T> setData(List<T> data) {
		this.data = data;
		return this;
	}

	public PageInfo<T> setPage(Page<T> page) {
		this.data = page.getResult();
		this.pageNo = page.getPageNum();
		this.setPageSize(page.getPageSize());
		this.totalCount = page.getTotal();
		return this;
	}

	/**
	 * 设置数据总数
	 *
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
