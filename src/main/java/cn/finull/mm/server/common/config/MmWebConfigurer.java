package cn.finull.mm.server.common.config;

import cn.finull.mm.server.common.interceptor.AdminLoginInterceptor;
import cn.finull.mm.server.common.interceptor.UserLoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MmWebConfigurer implements WebMvcConfigurer {

    private final MmConfig mmConfig;

    /**
     * 文件上传资源映射
     * @param registry ignore
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(mmConfig.getFileUploadAccessPath())
                .addResourceLocations(mmConfig.getFileUploadFolder());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login", "/api/register", "/api/codes/**");
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/api/**")
                .excludePathPatterns("/admin/login");
    }

    /**
     * 处理跨域
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildCorsConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        return config;
    }
}
