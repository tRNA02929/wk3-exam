package com.ksyun.start.camp;

import org.junit.Test;
import org.springframework.http.HttpEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestGetTime {
    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("serviceName", "test");
        map.put("ipAddress", "localhost");
        map.put("port", 8080);
        map.put("serviceId", 123456);
        HttpEntity<HashMap<String, Object>> request = new HttpEntity<>(map);
        System.out.println(request.getBody());
        System.out.println("Hello World!");
    }

    @Test
    public void testLocalDateTime() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        System.out.println(1690251417000L);
        System.out.println(Instant.now().toEpochMilli());
    }

    @Test
    public void testInstant() {
        SortedSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(5);
        set.add(3);
        set.removeIf(integer -> integer > 2);
        System.out.println(Arrays.toString(set.toArray()));
    }
}
