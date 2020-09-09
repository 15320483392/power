package com.wt.auth.server.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * @author WTar
 * @date 2020/3/19 11:40
 */
public class OauthClientExt implements ClientDetails {

    /**
     * 客户端id
     * @author wangtao
     * @date 2020/3/19 18:16
     * @param  * @param null
     * @return
     */
    private String clientId;

    /**
     * 资源权限
     * @author wangtao
     * @date 2020/3/19 18:17
     * @param  * @param null
     * @return
     */
    private Set<String> resourceIds = new HashSet<>();

    /**
     * 对应密钥
     * @author wangtao
     * @date 2020/3/19 18:16
     * @param  * @param null
     * @return
     */
    private String clientSecret;

    private Set<String> scope;

    /**
     * auth模式
     * @author wangtao
     * @date 2020/3/19 18:17
     * @param  * @param null
     * @return
     */
    private Set<String> authorizedGrantTypes = new HashSet<>();

    private Set<String> registeredRedirectUri = new HashSet<>();

    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    /**
     * token过期时间
     * @author wangtao
     * @date 2020/3/19 18:17
     * @param  * @param null
     * @return
     */
    private Integer accessTokenValiditySeconds = 28800;

    /**
     * 刷新token过期时间
     * @author wangtao
     * @date 2020/3/19 18:17
     * @param  * @param null
     * @return
     */
    private Integer refreshTokenValiditySeconds = 720000;

    private Map<String, Object> additionalInformation = new HashMap<>();

    private Set<String> autoApproveScopes;

    public OauthClientExt setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    public OauthClientExt setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }


    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    public OauthClientExt setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    public OauthClientExt setScope(Set<String> scope) {
        this.scope = scope;
        return this;
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    public OauthClientExt setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
        return this;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    public OauthClientExt setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri == null?null:new LinkedHashSet(registeredRedirectUri);
        return this;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUri;
    }

    public OauthClientExt setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = new ArrayList(authorities);
        return this;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public OauthClientExt setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        return this;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    public OauthClientExt setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        return this;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValiditySeconds;
    }

    public OauthClientExt setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
        return this;
    }

    public Set<String> getAutoApproveScopes() {
        return this.autoApproveScopes;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if(this.autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = this.autoApproveScopes.iterator();
            String auto;
            do {
                if(!var2.hasNext()) {
                    return false;
                }
                auto = (String)var2.next();
            } while(!auto.equals("true") && !scope.matches(auto));
            return true;
        }
    }

    public OauthClientExt setAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
        return this;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }

    @Override
    public String toString() {
        final StringBuilder model = new StringBuilder("{");
        model.append("\"clientId\":\"").append(clientId).append('\"');
        model.append(",\"resourceIds\":").append(resourceIds);
        model.append(",\"clientSecret\":\"").append(clientSecret).append('\"');
        model.append(",\"scope\":").append(scope);
        model.append(",\"authorizedGrantTypes\":").append(authorizedGrantTypes);
        model.append(",\"registeredRedirectUri\":").append(registeredRedirectUri);
        model.append(",\"authorities\":").append(authorities);
        model.append(",\"accessTokenValiditySeconds\":").append(accessTokenValiditySeconds);
        model.append(",\"refreshTokenValiditySeconds\":").append(refreshTokenValiditySeconds);
        model.append(",\"additionalInformation\":").append(additionalInformation);
        model.append(",\"autoApproveScopes\":").append(autoApproveScopes);
        model.append('}');
        return model.toString();
    }
}
