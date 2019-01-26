/**
 * 
 */
package com.example.springbatch.configuration;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author 86211
 *
 */
@Configuration
@ConfigurationProperties("mysql.datasource")
public class DataSourceConfiguration {

    @NotNull
    private DataSourceProperties common;

    private Map<String, DataSourceProperties> config;

    public DataSourceProperties getCommon() {
        return common;
    }

    public void setCommon(DataSourceProperties common) {
        this.common = common;
    }

    public Map<String, DataSourceProperties> getConfig() {
        return config;
    }

    public void setConfig(Map<String, DataSourceProperties> config) {
        this.config = config;
    }

    @Bean
    DataSource dataSource() throws SQLException {
        Map<Object, Object> allDataSources = TenantContext.getAllDataSources();
        Set<String> configSet = config.keySet();
        TenantContext.updateTenantList(configSet);
        configSet.forEach(tenant -> {
            DataSourceProperties dataSourceProperties = config.get(tenant);
            DataSource dataSource = createDataSource(dataSourceProperties);
            allDataSources.put(tenant, dataSource);
        });
        MultitenantDataSource multitenantDataSource = new MultitenantDataSource();
        multitenantDataSource.setDefaultTargetDataSource(createDataSource(this.common));
        multitenantDataSource.setTargetDataSources(TenantContext.getAllDataSources());
        multitenantDataSource.afterPropertiesSet();
        return multitenantDataSource;

    }

    /**
     * @param dataBaseProperties
     * @return
     */
    private DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(dataSourceProperties.getUsername());
        hikariDataSource.setPassword(dataSourceProperties.getPassword());
        hikariDataSource.setJdbcUrl(dataSourceProperties.getUrl());
        hikariDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        return hikariDataSource;
    }
}
