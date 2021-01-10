/**
 * Author: zhengyou
 * Date:   2019/1/3 17:09
 * Descripition:
 */
package com.newlife.charge.core.dto.in;


import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "推广建站查询参数接收类")
public class GeneralizeStationLogQueryIn extends Pageable {


}
