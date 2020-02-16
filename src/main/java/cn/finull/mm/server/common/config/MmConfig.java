package cn.finull.mm.server.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description
 * <p> 文件上传配置
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "mm-config")
public class MmConfig {

    /**
     * 文件上传的路径
     */
    private String fileUploadFolder;

    /**
     * 上传文件的资源访问路径
     */
    private String fileUploadAccessPath;

    /**
     * 访问上传资源的前缀
     */
    private String fileHttpPrefix;

    /**
     * rsa公钥
     */
    private String rsaPublicKey;

    /**
     * rsa私钥
     */
    private String rsaPrivateKey;

    /**
     * 用户默认密码
     */
    private String userDefaultPwd;
}
