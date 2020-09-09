package com.wt.power.wtserver.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WTar
 * @date 2020/9/5 16:07
 */
@Data
@Accessors(chain = true)
public class LoginList {

    private String name;

    private String url;

    public LoginList() {
    }

    public LoginList(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
