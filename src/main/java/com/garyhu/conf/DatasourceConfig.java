package com.garyhu.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author: garyhu
 * @since: 2018/11/22 0022
 * @decription:
 */
@Configuration
public class DatasourceConfig {

    @Bean(name = "myDataSource")
    @Qualifier("myDataSource")
    @ConfigurationProperties(prefix="spring.datasource.other")
    public DataSource getMyDataSource(){
        return DataSourceBuilder.create().build();
    }
}
