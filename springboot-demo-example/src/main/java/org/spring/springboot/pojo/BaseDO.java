package org.spring.springboot.pojo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/19 上午12:27
 */
public class BaseDO implements Serializable {
    private static final long serialVersionUID = -1406955511123707492L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}