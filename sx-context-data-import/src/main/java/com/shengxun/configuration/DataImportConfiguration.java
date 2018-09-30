package com.shengxun.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by chenwei
 * on 2018/6/21 17:55.
 */
@Component
@ConfigurationProperties(prefix = "data.path")
@Data
public class DataImportConfiguration {
    private String prefix;
    private String source;
    private String target;

}
