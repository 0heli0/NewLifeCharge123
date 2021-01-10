/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.newlife.charge.dao.common;

import org.apache.ibatis.annotations.Param;

/**
 * DAO支持类实现
 */
public interface CrudRepository<T> {

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    T get(int id);

    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 删除数据
     *
     * @param entity
     * @return
     */
    int delete(T entity);

    /**
     * 删除数据
     *
     * @param ids
     * @return
     */
    void deleteByIds(@Param("ids") int[] ids);


}
