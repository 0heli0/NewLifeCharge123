package com.newlife.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.core.domain.Order;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.domain.exModel.TruckSpaceEx;
import com.newlife.charge.core.dto.in.PileHistoryDataIn;
import com.newlife.charge.core.dto.in.PileRealTimeDataIn;
import com.newlife.charge.core.dto.out.ChargeBeginOut;
import com.newlife.charge.core.dto.out.ChargeEndOut;
import com.newlife.charge.core.dto.out.PlugInGunOut;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.ClientStationMapper;
import com.newlife.charge.dao.OrderMapper;
import com.newlife.charge.dao.StationClientGunMapper;
import com.newlife.charge.service.WebSocketChargeService;
import com.newlife.charge.service.WebSocketService;
import com.newlife.charge.service.webSocket.ChargeBeginOutEncoder;
import com.newlife.charge.service.webSocket.ChargeEndOutEncoder;
import com.newlife.charge.service.webSocket.PlugInGunOutEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;


@ServerEndpoint(value = "/api/websocket/{sid}/{gunId}",encoders = {PlugInGunOutEncoder.class, ChargeEndOutEncoder.class, ChargeBeginOutEncoder.class})
@Component
public class WebSocketServerImpl implements WebSocketService{

    static Logger log= LoggerFactory.getLogger(WebSocketServerImpl.class);

    // 绑定枪和用户id
    private static final String Bind_Gun_User_Prefix = "gun_user_id_";

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServerImpl> webSocketSet = new CopyOnWriteArraySet<WebSocketServerImpl>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";

    private String gunId="";

    @Autowired
    private ClientStationMapper clientStationMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StationClientGunMapper stationClientGunMapper;

    @Autowired
    private WebSocketChargeService webSocketChargeService;




    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid, @PathParam("gunId") String gunId) {


        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        log.info("绑定的充电枪为:"+gunId+"号枪");
        this.sid=sid;
        this.gunId=gunId;
        try {
            // 通过用户id与充电枪id绑定
            log.info("===> 通过用户id与充电枪id绑定");
            RedisUtils.setRedisValue(Bind_Gun_User_Prefix+gunId, sid, 24, TimeUnit.HOURS);
        sendMessage("连接成功");
        } catch (IOException e) {
        log.error("websocket IO异常");
        }
    }

    /**
    * 连接关闭调用的方法
    */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        RedisUtils.deleteRedisValue(Bind_Gun_User_Prefix+gunId); // 删除redis相应数据
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
    * 收到客户端消息后调用的方法
    *
    * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口"+sid+"的信息:"+message);
        //群发消息
        for (WebSocketServerImpl item : webSocketSet) {
            try {
            item.sendMessage(message);
            } catch (IOException e) {
            e.printStackTrace();
            }
         }
    }

    /**
    *
    * @param session
    * @param error
    */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
    * 实现服务器主动推送
    */
    public  void sendMessage(String message) throws IOException {
        log.info("===> message:"+message);
        this.session.getBasicRemote().sendText(message);
    }
    /**
     * 实现服务器主动推送
     */
    public  void sendObject(Object object) throws IOException {
        log.info("===> object:"+object);
        try {
            this.session.getBasicRemote().sendObject(object);
        }catch (EncodeException e){
            e.printStackTrace();
            log.info("===> 对象传输错误");
        }

    }

    /**
     * 实现服务器主动推送到指定用户
     */
    public static void sendMessage(String sid, Object object) throws IOException {
        for (WebSocketServerImpl item : webSocketSet) {
            if(item.sid.equals(sid)) {
                String jsonStr = JSONObject.toJSONString(object);
                if(jsonStr.length()>1){
                    jsonStr = jsonStr.substring(1,jsonStr.length()-1);
                }
                log.info("===> str:"+jsonStr);

                item.sendMessage(jsonStr);
            }
        }
    }

    /**
     * 实现服务器主动推送到指定用户(临时)
     */
    public static void sendMessage(String sid, Object object, Integer type) throws IOException {
        for (WebSocketServerImpl item : webSocketSet) {
            if(item.sid.equals(sid)) {
                String jsonStr = JSONObject.toJSONString(object);
                if(jsonStr.length()>1){
                    jsonStr = jsonStr.substring(1,jsonStr.length()-1);
                    jsonStr = "{"+jsonStr+"}";
                }
                log.info("===> str:"+jsonStr);

                item.sendMessage(jsonStr);
            }
        }
    }

    /**
    * 群发自定义消息(暂时取消群发)
    * */
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException{
        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocketServerImpl item : webSocketSet) {
            try {
            //这里可以设定只推送给这个sid的，为null则全部推送
            if(sid==null) {
//                item.sendMessage(message);
                }else if(item.sid.equals(sid)){
            item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static void closeByuser(@PathParam("sid") String sid){
        log.info("关闭webSocket，用户："+sid);
        for (WebSocketServerImpl item : webSocketSet) {
            if(item.sid.equals(sid)){
                item.onClose();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
     WebSocketServerImpl.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServerImpl.onlineCount--;
    }

    /**
     * 实时数据
     * @param id
     * @param status 充电枪状态 1."离线",2"空闲中",3"连接中",4"充电中",5"被预约",6"排队中"
     * @param pileRealTimeDataIn 充电枪实时数据
     */
    @Override
    public void sendRealTimeMessage(Integer id, Integer status, PileRealTimeDataIn pileRealTimeDataIn) {
        // 获取userId
        String key = Bind_Gun_User_Prefix+id;
        if(!RedisUtils.isExistRedis(key)){
            log.info("===> 该充电枪没有绑定用户，未进行充电操作");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        String userId = RedisUtils.getRedisValue(key);

        ChargeBeginOut out = this.webSocketChargeService.sendRealTimeMessage(id, status, pileRealTimeDataIn);
        try {
            sendMessage(userId, out);
        }catch (IOException e){
            log.info("===> 发送实时数据时发生io异常：");
            e.printStackTrace();
            return;
        }
    }

    /**
     * 插枪
     * @param id     充电枪ID
     * @param status 充电枪状态 1."离线",2"空闲中",3"连接中",4"充电中",5"被预约",6"排队中"
     * @param pileRealTimeDataIn 充电枪实时数据
     */
    @Override
    public void plugInGun(Integer id, Integer status, PileRealTimeDataIn pileRealTimeDataIn) {
        // 获取userId
        String key = Bind_Gun_User_Prefix+id;
        log.info("===> key:"+key);
        if(!RedisUtils.isExistRedis(key)){
            log.info("===> 该充电枪没有绑定用户，未进行充电操作");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        String userId = RedisUtils.getRedisValue(key);

        // 将数据返回给客户端
        try {
            PlugInGunOut out = this.webSocketChargeService.plugInGun(id,status,pileRealTimeDataIn);
            sendMessage(userId, out);
        }catch (IOException e){
            log.info("===> 发送实时数据时发生io异常：");
            e.printStackTrace();
            return;
        }

    }

    /**
     * 充电成功
     * @param infoIn
     */
    @Override
    public void chargeStopSuccess(PileHistoryDataIn infoIn){

        log.info("===> 进入充电结束结算操作");

        // 或者通过交易单号--订单id查询order
       Integer orderId = infoIn.getRecordNo();
        log.info("===> 查询订单");
        Order order = orderMapper.get(orderId);
        // 将结束后的数据发送到客户端
        // 将数据返回给客户端
        log.info("===> 将数据返回给客户端");
        Integer userId = order.getUserId();
        ChargeEndOut out = this.webSocketChargeService.chargeStopSuccess(infoIn);
        try {

            sendMessage(userId+"", out);

            // 关闭webSocket
            log.info("===> 关闭webSocket");
            closeByuser(userId+"");

        }catch (IOException e){
            log.info("===> 发送实时数据时发生io异常：");
            e.printStackTrace();
            closeByuser(order.getUserId()+"");
            return;
        }

    }

    /**
     * 获取redis车位key
     * @param prefix key前缀
     * @param gunId 枪id
     * @return key
     */
    public String getKey(String prefix, Integer gunId){

        // 查询充电枪详情
        TruckSpaceEx detail = this.clientStationMapper.truckSpaceDetail(gunId);

        if(detail == null){
            log.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getGunId();

        if(key == null){
            log.info("===> 车位信息错误");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return key;
    }

    /**
     * 获取redis车位key
     * @param prefix key前缀
     * @param gunId 充电枪id
     * @return key
     */
    public String getKeyByGun(String prefix, Integer gunId){

        // 查询充电枪详情
        StationClientGunEx detail = this.stationClientGunMapper.getInfoByParams(gunId,
                null, null, null, null, null);

        if(detail == null){
            log.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getId();

        if(key == null){
            log.info("===> 车位信息错误");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return key;
    }

    /**
     * 删除key
     * @param key
     */
    private void deleteRedisKey(String key){
        if(RedisUtils.isExistRedis(key)){
            RedisUtils.deleteRedisValue(key);
        }
    }

}