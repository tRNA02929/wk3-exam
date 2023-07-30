package com.ksyun.start.camp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 代表此 API 的返回对象
 */
@Data
@Builder
public class ApiResponse {

    private Long serviceId;

    private String result;

}
