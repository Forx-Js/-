package com.ht.bim.iam.core;

import com.ht.bim.iam.IamConfigDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by zeng.ling on 2019-3-26.
 */
@Service
public class IamClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IamConfigDataProvider iamConfigDataProvider;
    private IamConfig iamConfig;

    @PostConstruct
    public synchronized void init() {
        if (iamConfig == null) {
            iamConfig = iamConfigDataProvider.buildConfig();
        }
    }

    public <T> T post(String apiUrl, Object param) {
        String fullUrl = iamConfig.getBaseUrl() + apiUrl;
        IamResult<T> result = restTemplate.postForObject(fullUrl, param, IamResult.class);
        return result.payload();
    }

    public <T> T get(String apiUrl) {
        String fullUrl = iamConfig.getBaseUrl() + apiUrl;
        IamResult<T> result = restTemplate.getForObject(fullUrl, IamResult.class);
        return result.payload();
    }
}
