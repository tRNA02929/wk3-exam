package com.ksyun.start.camp;

import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认的客户端 API Controller
 */
@Slf4j
@RestController
public class ClientController {

    @RequestMapping("/")
    public String index() {
        return "this is client service";
    }
    // 在这里开始编写你的相关接口实现代码
    // 返回值对象使用 ApiResponse 类

    // 提示：调用 ClientService
    @GetMapping("/api/getInfo")
    public Object getInfo() {
        Object data = new ClientServiceImpl().getInfo();
        if (data == null) {
            log.error("获取信息失败");
            return RestResult.failure().data(data).descr("获取信息失败");
        }
        log.info("获取信息成功");
        return RestResult.success().data(data).descr("获取信息成功");
    }
}
