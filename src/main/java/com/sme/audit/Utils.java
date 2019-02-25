package com.sme.audit;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Utils {

    public static JedisPool getJedisPool(String host, int port) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setTestWhileIdle(false);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        poolConfig.setNumTestsPerEvictionRun(-1);

        System.out.println("connected to  redis " + host + ":" + port);
        JedisPool pool = new JedisPool(poolConfig, host, port, 10000);
        return pool;
    }
}
