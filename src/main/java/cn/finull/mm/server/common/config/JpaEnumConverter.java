package cn.finull.mm.server.common.config;

import cn.finull.mm.server.common.enums.BaseEnum;
import cn.hutool.core.util.StrUtil;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

/**
 * Description
 * <p> Jpa枚举类型转换
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:45
 */
public class JpaEnumConverter<T extends Enum<T> & BaseEnum<T>> implements AttributeConverter<T, String> {

    private final Class<T> clz;

    public JpaEnumConverter(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public String convertToDatabaseColumn(T t) {
        if (t == null) {
            throw new IllegalArgumentException("枚举向常量转换时参数错误！");
        }
        return t.getCode();
    }

    @Override
    public T convertToEntityAttribute(String s) {
        if (StrUtil.isBlank(s)) {
            throw new IllegalArgumentException("常量向枚举转换时参数错误！");
        }

        T[] enums = clz.getEnumConstants();

        return Stream.of(enums)
                .filter(t -> s.equals(t.getCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("常量向枚举转换时参数错误！"));
    }
}
