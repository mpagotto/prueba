package com.codapay.event;

import redis.clients.jedis.JedisPubSub;

import java.text.SimpleDateFormat;
import java.util.Date;
public class KeyExpiredEventListener extends JedisPubSub {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final StringBuilder sb = new StringBuilder();
    private int cnt;
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("Client Subscribed: "+pattern + ", channels: "+ subscribedChannels);
    }

    public void onMessage(String s, String s1) {
    }

    @Override
    public void onPMessage(String pattern, String channel, String msg) {
        sb.setLength(0);
        sb.append(String.format("%1$09d", ++cnt)).append(" [");
        sb.append(sdf.format(new Date(System.currentTimeMillis()))).append("] ").append(msg);
        System.out.println(sb.toString());
    }

    public void onSubscribe(String s, int i) {
    }

    public void onUnsubscribe(String s, int i) {
    }

    public void onPUnsubscribe(String s, int i) {
    }
}