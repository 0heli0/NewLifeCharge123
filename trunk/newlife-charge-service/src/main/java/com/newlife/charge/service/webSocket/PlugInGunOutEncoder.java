package com.newlife.charge.service.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.newlife.charge.core.dto.out.PlugInGunOut;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class PlugInGunOutEncoder implements Encoder.Text<PlugInGunOut>  {
    //代表websocket调用sendObject方法返回客户端的时候，必须返回的是DomainResponse对象
    @Override
    public String encode(PlugInGunOut object) {
        //将java对象转换为json字符串
        return "'"+JSONObject.toJSONString(object)+"'";
    }
    @Override
    public void init(EndpointConfig endpointConfig) { }

    @Override
    public void destroy() { }
}