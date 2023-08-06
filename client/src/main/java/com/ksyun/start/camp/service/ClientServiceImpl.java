package com.ksyun.start.camp.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.ksyun.start.camp.ServiceAppRunner.serviceId;

/**
 * 客户端服务实现
 */
@Component
public class ClientServiceImpl implements ClientService {


    @Override
    public Object getInfo() {

        // 开始编写你的逻辑，下面是提示
        // 1. 调用 TimeService 获取远端服务返回的时间
        // 2. 获取到自身的 serviceId 信息
        // 3. 组合相关信息返回
        String datetime = new TimeServiceImpl().getDateTime("unix");
        if (datetime == null) {
            return null;
        }
        String result = "Hello Kingsoft Clound Star Camp - [" +
                serviceId + "] - " + datetime.substring(0, 19);
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return map;
    }
}
