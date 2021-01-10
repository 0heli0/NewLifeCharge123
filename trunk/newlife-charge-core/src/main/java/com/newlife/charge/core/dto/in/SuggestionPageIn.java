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

/**
 * 用户反馈分页查询
 */
@Getter
@Setter
@ApiModel
public class SuggestionPageIn extends Pageable {

}
