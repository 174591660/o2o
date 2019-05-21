package com.imooc.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zy on 2019/1/23
 */
public class JedisPoolWriper {

    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host,
                           final int port) {
        try {
            jedisPool = new JedisPool(poolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
