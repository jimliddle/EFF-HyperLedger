package com.sme.audit.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.logging.Level;
import java.util.logging.Logger;


public class RedisAuditHandler {
    JedisPool pool ;
    String channel ;
    Jedis jedis;
    JedisPubSub listener;


    private final static Logger LOGGER = Logger.getLogger(RedisAuditHandler.class.getName());


    public RedisAuditHandler setJedisPool(JedisPool jedisPool)  {
        this.pool = jedisPool;
        return this;
    }

    public RedisAuditHandler setChannel(String channel) {
        this.channel= channel;
        return this;
    }


    public RedisAuditHandler setListener(JedisPubSub listener) {
        this.listener = listener;
        return this;
    }

    public void start() {
            try {
                jedis = pool.getResource();
                jedis.subscribe(listener, channel);
            } catch (JedisConnectionException jce) {
                LOGGER.log(Level.SEVERE, jce.getMessage(), jce);
            } finally {
                jedis.close();
            }
    }

    public void stop() {
        //ToDo implement unsubscribe
        //listener.unsubscribe(channel);
    }


}

