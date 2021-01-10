package com.newlife.charge.service.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.newlife.charge.core.dto.out.ChargeBeginOut;
import com.newlife.charge.core.dto.out.PlugInGunOut;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class ChargeBeginOutEncoder implements Encoder.Text<ChargeBeginOut>  {
    //代表websocket调用sendObject方法返回客户端的时候，必须返回的是DomainResponse对象
    @Override
    public String encode(ChargeBeginOut object) {
        //将java对象转换为json字符串
        return "'"+JSONObject.toJSONString(object)+"'";
    }
    @Override
    public void init(EndpointConfig endpointConfig) { }

    @Override
    public void destroy() { }
}