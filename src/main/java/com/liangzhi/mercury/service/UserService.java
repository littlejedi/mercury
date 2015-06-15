package com.liangzhi.mercury.service;

import com.liangzhi.commons.domain.platform.User;

public interface UserService {
    
    User findUserByEmail(String email) throws Exception;

}
