package com.ksyun.start.camp.service;

import com.ksyun.start.camp.ApiResponse;
import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.TimeService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * 代表远端时间服务接口实现
 */
@Component
public class TimeServiceImpl implements TimeService {

    @Override
    public String getDateTime(String style) {

        // 开始编写你的逻辑，下面是提示
        // 1. 连接到 registry 服务，获取远端服务列表
        // 2. 从远端服务列表中获取一个服务实例
        // 3. 执行远程调用，获取指定格式的时间
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> restResult = restTemplate.getForObject("http://localhost:8200/api/discovery/?serviceName=time-server", List.class);
        Map<String, Object> map = restResult.get(0);
//
//        RestResult<ApiResponse> result = restTemplate.getForObject("http://" +
//                map.getOrDefault("ipAddress", "") + ":" +
//                map.getOrDefault("port", "") +
//                "/api/getDateTime?style=" + style, RestResult.class);
//        return result.getData().getResult();
        return null;
    }
}
