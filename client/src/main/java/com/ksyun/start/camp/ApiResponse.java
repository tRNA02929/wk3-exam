package com.ksyun.start.camp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代表此 API 的返回对象
 */
@Data
@Builder
public class ApiResponse {

    private Long serviceId;

    private String result;

}
