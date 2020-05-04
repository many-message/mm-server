package cn.finull.mm.server.common.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Description
 * <p> 参数注入枚举值转换
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-02 0:36
 */
@Component
public class EnumConverterFactory implements ConverterFactory<String, Enum> {

    private static final Map<Class, Converter> CONVERTER_MAP = new WeakHashMap<>();

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = CONVERTER_MAP.get(targetType);
        if(result == null) {
            result = new EnumConverter<>(targetType);
            CONVERTER_MAP.put(targetType, result);
        }
        return result;
    }

    static class EnumConverter<T extends Enum> implements Converter<String, T> {

        private Class<T> enumType;

        private Map<String, T> enumMap = new HashMap<>();

        public EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                enumMap.put(e.toString(), e);
            }
        }

        @Override
        public T convert(String source) {
            return enumMap.get(source);
        }
    }
}
