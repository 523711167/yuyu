package com.xiaoxipeng.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 启用特性
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 注册模块
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(simpleModule());
        return objectMapper;
    }

    private SimpleModule simpleModule() {
        SimpleModule module = new SimpleModule();
        // Date 消息转换器
        module.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                // 对时间进行格式化，返回数据为字符串类型
                jsonGenerator.writeNumber(date.getTime());
            }
        });
        // LocalDate 消息转换器
        module.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate));
            }
        });
        // LocalTime 消息转换器
        module.addSerializer(LocalTime.class, new JsonSerializer<LocalTime>() {
            @Override
            public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ofPattern("HH:mm:ss").format(localTime));
            }
        });
        // LocalDateTime 消息转换器
        module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, IOException {
                jsonGenerator.writeNumber(localDateTime.toEpochSecond(ZoneOffset.of("+8")));
            }
        });

        return module;
    }

}
