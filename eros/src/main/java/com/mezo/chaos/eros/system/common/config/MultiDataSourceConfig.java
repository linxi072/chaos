package com.mezo.chaos.eros.system.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author mezo
 */
@Configuration
public class MultiDataSourceConfig {
    @Primary
    @Bean(name = "oneDataSource")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean(name = "twoDataSource")
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}

