package com.liangzhi.mercury.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.elasticsearch.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liangzhi.commons.domain.platform.User;
import com.liangzhi.mercury.cache.UserCacheLoader;
import com.liangzhi.mercury.dao.UserDao;

@Component
public class UserServiceBean implements UserService {
    
    @Autowired
    private UserDao userDao;
            
    private LoadingCache<String, User> userCache;
    
    @PostConstruct
    public void init() {
        Preconditions.checkNotNull(userDao);
        userCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build(new UserCacheLoader(userDao));
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return userCache.get(email);
    }

}
