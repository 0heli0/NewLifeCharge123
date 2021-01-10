/* -------------------------------------------
 * UserCouponQueryIn.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import lombok.Getter;
import lombok.Setter;


/**
 * 用户查看可领取优惠券mapper输入类
 */

@Getter
@Setter
public class UserCouponQuery extends Pageable{

    // 是否是天降神券接口,必填,非空(0:用户手动点击按钮时传入, 1:天降神券时传入)
    private Integer autoIndex;

    // 用户id
    private Integer userId;

    // 桩站id
    private Integer stationId;


}