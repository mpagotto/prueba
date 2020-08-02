package com.codapay.event;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHelper {

    private static RedisHelper instance;
    JedisPool jedisPool;

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1024);
        poolConfig.setMaxIdle(512);
        poolConfig.setMinIdle(32);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60*1000);
        poolConfig.setTimeBetweenEvictionRunsMillis(60*1000);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }


    private RedisHelper(){
        final JedisPoolConfig poolConfig = buildPoolConfig();
        jedisPool = new JedisPool(poolConfig, "localhost",6379);
    }
    void set(String id, Integer seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(id,seconds,"expireAt: " + System.currentTimeMillis()/1000 + seconds);
        }
    }
    public static synchronized RedisHelper getInstance(){
        if(instance == null){
            instance = new RedisHelper();
        }
        return instance;
    }

    public void subscribe() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.psubscribe(new KeyExpiredEventListener(), "__key*__:*");
        }
    }




}
