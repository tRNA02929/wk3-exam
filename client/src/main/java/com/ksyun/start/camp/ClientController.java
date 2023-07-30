package com.ksyun.start.camp;

import com.ksyun.start.camp.service.ClientServiceImpl;
import com.ksyun.start.camp.service.TimeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认的客户端 API Controller
 */
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
        return new ClientServiceImpl().getInfo();
    }
}
