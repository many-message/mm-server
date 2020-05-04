package cn.finull.mm.server.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Description
 * <p> Jackson 配置
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-03 15:21
 */
@Configuration
public class JacksonConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateFormatPattern;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer configureObjectMapper() {
        return builder -> {
            // 时间格式序列化
            JavaTimeModule jtm = new JavaTimeModule();
            jtm.addSerializer(Date.class, new DateSerializer());
            jtm.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateFormatPattern))
            );
            jtm.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateFormatPattern)));

            // Long丢失精度处理
            SimpleModule ltm = new SimpleModule();
            ltm.addSerializer(Long.class, ToStringSerializer.instance);
            ltm.addSerializer(Long.TYPE, ToStringSerializer.instance);

            builder.failOnUnknownProperties(false)
                    .modules(jtm, ltm)
                    .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                    .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                    .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                    .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                    .propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        };
    }
}
