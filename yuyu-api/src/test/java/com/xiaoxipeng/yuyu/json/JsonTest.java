package com.xiaoxipeng.yuyu.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoxipeng.user.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JsonTest {


    @Test
    public void test() throws JsonProcessingException {
        // 默认返回 null
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new Jdk8Module());
//        objectMapper.registerModule(new JavaTimeModule());


        User user = new User();
        user.setId(1L);
        user.setAddress("北京");
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        user.setBirthDate(LocalDate.now());

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);


    }
}
