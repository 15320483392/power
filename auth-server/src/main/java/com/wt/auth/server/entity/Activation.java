package com.wt.auth.server.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.wt.auth.server.listenter.CDATAConvert;

/**
 * @author WTar
 * @date 2020/3/18 11:53
 */
@XStreamAlias("activation")
public class Activation {

    /**
     * 激活码
     * @author wangtao
     * @date 2020/3/19 18:18
     * @param  * @param null
     * @return
     */
    @XStreamConverter(CDATAConvert.class)
    private String code;

    /**
     * 时间
     * @author wangtao
     * @date 2020/3/19 18:18
     * @param  * @param null
     * @return
     */
    @XStreamConverter(CDATAConvert.class)
    private String createTime;

    /**
     * 密钥
     * @author wangtao
     * @date 2020/4/1 10:52
     * @param  * @param null
     * @return
     */
    @XStreamConverter(CDATAConvert.class)
    private String companyKey;

    public String getCode() {
        return code;
    }

    public Activation setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Activation setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public Activation setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder model = new StringBuilder("{");
        model.append("\"code\":\"").append(code).append('\"');
        model.append(",\"createTime\":\"").append(createTime).append('\"');
        model.append(",\"companyKey\":\"").append(companyKey).append('\"');
        model.append('}');
        return model.toString();
    }
}
