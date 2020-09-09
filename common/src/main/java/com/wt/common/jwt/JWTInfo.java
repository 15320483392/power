package com.wt.common.jwt;

/**
 * @author WTar
 * @date 2020/3/9 9:32
 */
public class JWTInfo {

    /**
     * 登录账号
     * @author wangtao
     * @date 2020/3/9 9:36
     * @param  * @param null
     * @return
     */
    private String userName;

    /**
     * 用户id
     * @author wangtao
     * @date 2020/3/9 9:36
     * @param  * @param null
     * @return
     */
    private String userId;

    /**
     * 权限id
     * @author wangtao
     * @date 2020/3/9 9:36
     * @param  * @param null
     * @return
     */
    private Integer roleId;

    /**
     * 登录id
     * @author wangtao
     * @date 2020/3/9 9:36
     * @param  * @param null
     * @return
     */
    private String loginId;

    /**
     * 姓名
     * @author wangtao
     * @date 2020/3/9 9:36
     * @param  * @param null
     * @return
     */
    private String name;

    /**
     * 登录客户端
     * @author wangtao
     * @date 2020/3/9 9:39
     * @param  * @param null
     * @return
     */
    private String client;

    /**
     * 用户类型
     * @author wangtao
     * @date 2020/3/9 9:39
     * @param  * @param null
     * @return
     */
    private Integer type;

    /**
     * 该用户所属公司
     * @author wangtao
     * @date 2020/3/9 9:40
     * @param  * @param null
     * @return
     */
    private String companyId;

    /**
     * 公司名称
     * @author wangtao
     * @date 2020/3/26 10:53
     * @param  * @param null
     * @return
     */
    private String companyName;

    public JWTInfo() {
    }

    public JWTInfo(String userName, String userId, Integer roleId, String loginId, String name, String client, Integer type, String companyId) {
        this.userName = userName;
        this.userId = userId;
        this.roleId = roleId;
        this.loginId = loginId;
        this.name = name;
        this.client = client;
        this.type = type;
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public JWTInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public JWTInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public JWTInfo setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getLoginId() {
        return loginId;
    }

    public JWTInfo setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public String getName() {
        return name;
    }

    public JWTInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getClient() {
        return client;
    }

    public JWTInfo setClient(String client) {
        this.client = client;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public JWTInfo setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public JWTInfo setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public JWTInfo setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder model = new StringBuilder("{");
        model.append("\"userName\":\"").append(userName).append('\"');
        model.append(",\"userId\":\"").append(userId).append('\"');
        model.append(",\"roleId\":").append(roleId);
        model.append(",\"loginId\":\"").append(loginId).append('\"');
        model.append(",\"name\":\"").append(name).append('\"');
        model.append(",\"client\":\"").append(client).append('\"');
        model.append(",\"type\":").append(type);
        model.append(",\"companyId\":").append(companyId);
        model.append(",\"companyName\":\"").append(companyName).append('\"');
        model.append('}');
        return model.toString();
    }
}
