package com.wt.auth.server.server.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 单列缓存激活码数据
 * @author WTar
 * @date 2020/3/19 15:21
 */
@Slf4j
@Service
public class InMemoryClientDetailsService implements ClientDetailsService {

    private Map<String, ClientDetails> clientDetailsStore = new HashMap();

    public InMemoryClientDetailsService() {
    }

    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details = (ClientDetails)this.clientDetailsStore.get(clientId);
        if (details == null) {
            throw new NoSuchClientException("您暂无权限");
        } else {
            return details;
        }
    }

    public void setClientDetailsStore(Map<String, ? extends ClientDetails> clientDetailsStore) {
        this.clientDetailsStore = new HashMap(clientDetailsStore);
    }
}
