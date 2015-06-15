package com.liangzhi.mercury.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.liangzhi.commons.api.client.CoreApiClient;
import com.liangzhi.commons.domain.Link;
import com.liangzhi.commons.domain.Paginator;
import com.liangzhi.mercury.config.CoreApiClientConfiguration;

public class EntityDao {
    
private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Autowired
    private CoreApiClientConfiguration configuration;
    
    @Autowired
    protected CoreApiClient coreApiClient;
    
    public void setCoreApiClient(CoreApiClient coreApiClient) {
        this.coreApiClient = coreApiClient;
    }
    
    // Utility methods  
    // Resolve ALL objects from paginator, this includes going through all pages
    protected <T> List<T> resolveObjectsFromPaginator(Paginator paginator, Class<T> clazz) {
        Preconditions.checkState(paginator != null);
        List<T> objects = Lists.newArrayList();
        Paginator currentPaginator = paginator;
        List<? extends Object> currentResultSet = paginator.getResult();
        while (currentResultSet != null && currentResultSet.size() > 0) {
            for (Object o : currentResultSet) {
                objects.add(MAPPER.convertValue(o, clazz));
            }
            final String nextPageLink = currentPaginator.getNext();
            if (!StringUtils.isEmpty(nextPageLink)) {
                currentPaginator = coreApiClient.getWithFullUrl(nextPageLink, Paginator.class);
                currentResultSet = currentPaginator.getResult();
            } else {
                break;
            }
        }
        return objects;
    }
    
    // Resolve only the current paginator
    protected <T> List<T> resolveObjectsFromCurrentPaginator(Paginator paginator, Class<T> clazz) {
        Preconditions.checkState(paginator != null);
        List<T> objects = Lists.newArrayList();
        List<? extends Object> currentResultSet = paginator.getResult();
        if (currentResultSet != null && currentResultSet.size() > 0) {
            for (Object o : currentResultSet) {
                objects.add(MAPPER.convertValue(o, clazz));
            }
        }
        return objects;
    }
    
    protected <T> List<T> resolveLinks(List<Link> links, Class<T> clazz) {
        Preconditions.checkState(links != null);
        List<T> objects = Lists.newArrayList();
        for (Link link : links) {
            T obj = coreApiClient.getWithFullUrl(link.getHref(), clazz);
            objects.add(obj);
        }
        return objects;
    }

}
