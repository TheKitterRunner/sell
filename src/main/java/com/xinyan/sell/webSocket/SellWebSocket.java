package com.xinyan.sell.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Nico
 * 2018/11/23
 *
 * 服务端向客户端推送消息的webSocket
 */
@Slf4j
@ServerEndpoint("/webSocket")  // 发布
@Component
public class SellWebSocket {

    private Session session;

    private static CopyOnWriteArraySet<SellWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    public void sendMessage(String message){
        for (SellWebSocket sellWebSocket : webSocketSet){
            log.info("[WebSocket消息] 广播的消息, message : {}", message);
            try {
                // 向客户端发送消息
                sellWebSocket.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("[WebSocket消息] 有新的连接消息, 总连接数 : {}", webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("[WebSocket消息] 连接断开, 总连接数 : {}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("[WebSocket消息] 收到客户端发来的消息, 总连接数 : {}", message);
    }

}
