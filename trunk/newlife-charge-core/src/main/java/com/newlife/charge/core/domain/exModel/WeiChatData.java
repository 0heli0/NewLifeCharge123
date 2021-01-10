package com.newlife.charge.core.domain.exModel;


import lombok.Getter;
import lombok.Setter;

/**
 * 记录的微信用户信息
 * <p>
 */
@Getter
@Setter
public class WeiChatData {

    // 不加区号的号码
    private String purePhoneNumber;

    // 电话号码（加区号）
    private String phoneNumber;

    // 区号
    private String countryCode;

    // 水印标识
    private WeiChatWaterMark watermark;
}
