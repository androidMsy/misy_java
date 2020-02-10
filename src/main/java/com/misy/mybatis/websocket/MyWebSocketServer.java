package com.misy.mybatis.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.misy.mybatis.MybatisApplication;
import com.misy.mybatis.entity.ChatEntity;
import com.misy.mybatis.service.ChatService;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

import static com.misy.mybatis.contacts.WebSocketContacts.IMAGE_MESSAGE_TYPE;
import static com.misy.mybatis.contacts.WebSocketContacts.TEXT_MESSAGE_TYPE;

@ServerEndpoint("/myWebSocketServer/{sendUserId}")
@Component
public class MyWebSocketServer {

    ChatService chatService;

    private Session mySession;
    private static ConcurrentHashMap<String, Session> sessionMaps = new ConcurrentHashMap<>();
    private static int onlineCounts = 0;

    @OnOpen
    public void open(Session session,@PathParam("sendUserId")String sendUserId){
        this.mySession = session;
        sessionMaps.put(sendUserId, session);
        addOnlineCounts();
        if (null == chatService){
            chatService = MybatisApplication.getInstance().getBean(ChatService.class);
        }
        sendMessage("连接成功", session);
    }
    @OnMessage//收到消息执行
    public void onMessage(String message,Session session) {
        ChatEntity entity = JSON.parseObject(message, ChatEntity.class);
        sendMessage(entity);
    }
    @OnClose//关闭连接执行
    public void onClose(Session session) {
        sessionMaps.remove(session);
        subOnlineCounts();
    }
    @OnError//连接错误的时候执行
    public void onError(Throwable error,Session session) {
//        if(null != session){
//            session.getAsyncRemote().sendText(error.getMessage());
//        }
        error.printStackTrace();
    }
    /*
    websocket  session发送文本消息有两个方法：getAsyncRemote()和
   getBasicRemote()  getAsyncRemote()和getBasicRemote()是异步与同步的区别，
   大部分情况下，推荐使用getAsyncRemote()。
    */
    public void sendMessage(String message, Session session){
        session.getAsyncRemote().sendText(message);
    }
    /**
     * 指定ID发送消息
     * @param chatEntity
     */
    public void sendMessage(ChatEntity chatEntity)  {
        Session session = sessionMaps.get(chatEntity.getTargetUserId());
        chatEntity.setCreateDate(System.currentTimeMillis());
        if (chatEntity.getMessageType() == TEXT_MESSAGE_TYPE){//文本

        }else if (chatEntity.getMessageType() == IMAGE_MESSAGE_TYPE){//图片

        }
        if (null != session){
            chatEntity.setIsOnline(1);
            session.getAsyncRemote().sendText(JSONObject.toJSONString(chatEntity));
        }else {//离线操作
            chatEntity.setIsOnline(0);
            mySession.getAsyncRemote().sendText("该用户不存在或者不在线");
        }
        chatService.save(chatEntity);
    }
    public static synchronized int getOnlineCounts(){
        return onlineCounts;
    }
    public static synchronized void addOnlineCounts(){
        MyWebSocketServer.onlineCounts++;
    }
    public static synchronized void subOnlineCounts(){
        MyWebSocketServer.onlineCounts--;
    }
}
