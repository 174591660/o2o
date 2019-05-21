package com.imooc.o2o.service.impl;

import com.imooc.o2o.cache.JedisUtil;
import com.imooc.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by zy on 2019/1/24
 */
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keys = jedisKeys.keys(keyPrefix + "*");
        for (String key : keys) {
            jedisKeys.del(key);
        }

    }
}
