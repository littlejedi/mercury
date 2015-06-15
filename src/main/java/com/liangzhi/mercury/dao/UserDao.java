package com.liangzhi.mercury.dao;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.liangzhi.commons.api.CoreApiPath;
import com.liangzhi.commons.domain.platform.User;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Component
public class UserDao extends EntityDao {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    
    public User findUserByEmail(String email) {
        Preconditions.checkState(email != null, "Email should not be null");
        URI uri = UriBuilder.fromPath(CoreApiPath.USERS.getPath()).path("findByEmail").build();
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("email", email);
        User user = coreApiClient.getWithQueryParams(uri.toASCIIString(), User.class, queryParams);
        return user;
    }

}
