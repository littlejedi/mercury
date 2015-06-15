package com.liangzhi.mercury.cache;

import com.google.common.cache.CacheLoader;
import com.liangzhi.commons.domain.platform.User;
import com.liangzhi.mercury.dao.UserDao;

public class UserCacheLoader extends CacheLoader<String, User> {

    private UserDao userDao;
    
    public UserCacheLoader(UserDao userDao) {
        this.userDao = userDao;
    }
   
    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User load(String email) throws Exception {
        User user =  userDao.findUserByEmail(email);
        return user;
    }

}
