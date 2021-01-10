/**
 * Author: zhengyou
 * Date:   2018/12/11 15:51
 * Descripition:地区Service类
 */
package com.newlife.charge.service;

import com.newlife.charge.core.dto.out.AreaOut;

import java.util.List;

public interface AreaService {


    /**
     * 城市列表数据
     *
     * @param parentId 父级ID为空则返回最顶级数据
     * @return
     */
    List<AreaOut> list(Integer parentId);


}
