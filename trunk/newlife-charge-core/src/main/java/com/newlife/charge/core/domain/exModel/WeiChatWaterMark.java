package com.newlife.charge.core.domain.exModel;


import com.newlife.charge.core.domain.User;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信用户水签名信息
 * <p>
 */
@Getter
@Setter
public class WeiChatWaterMark extends User {

    // 时间戳
    private String timestamp;

    // 用户识别码
    private String appid;

    private String appid2;

}
