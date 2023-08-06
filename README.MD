# 星云训练营 - 第三周作业 - 微服务

---

作业模板: https://kingsoft-cloud.feishu.cn/docx/CfKOdC79moeDkYxRQc4cgy55n6e

---

## 作业要求

### 1、简单服务注册中心 (register)，端口为 8180

| 端点              | 功能说明    | 是否实现 | 备注                  |
|-----------------|---------|------|---------------------|
| /api/register   | 服务注册    | ✅    |                     |
| /api/unregister | 服务注销    | ✅    | 全匹配才能注销             |
| /api/heartbeat  | 客户端发送心跳 | ✅    | 只有在访问时才会清除过期服务(懒清除) |
| /api/discovery  | 服务发现    | ✅    |                     |

~~~
使用线程安全的ConcurrentSkipListSet存储服务信息，通过pollCount代表服务轮询次数，利用ConcurrentSkipListSet的排序作为轮询的依据
~~~

---

### 2、简单时间服务 (服务名 time-service)，端口分别为 8280，8281

| 端点               | 功能说明            | 是否实现 | 备注                    |
|------------------|-----------------|------|-----------------------|
| /api/getDateTime | 获取当前日期时间信息及节点信息 | ✅    | 若果参数为null,""则返回full时间 |

### 3、服务客户端 (服务名 client)，端口分别为 8300, 8301

| 端点           | 功能说明    | 是否实现 | 备注 |
|--------------|---------|------|----|
| /api/getInfo | 获取客户端信息 | ✅    |    |

---

+ client和time-service的serviceId通过uuid生成
+ client和time-service在register中注册失败，心跳发送时，都会不断重试

---

### 4、分布式日志收集服务端（服务名 logging-service），端口为 8320

| 端点           | 功能说明     | 是否实现 | 备注                |
|--------------|----------|------|-------------------|
| /api/logging | 记录日志信息   | ✅    | 提交的日志可以去重（全字段相同时） |
| /api/list    | 获取日志信息列表 | ✅    |                   |

~~~
使用线程安全的ConcurrentSkipListSet存储日志信息，通过原子类生成的logId进行排序
~~~

---
