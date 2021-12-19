package com.bruce121.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    static {
        // 加载驱动类，会自动执行静态代码块中的内容
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {

        // 获得连接
        String url = "jdbc:mysql://localhost:33106/test";
        String user = "root";
        String password = "123456";

        // 使用线程池获取连接
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setAutoCommit(true);
        dataSource.setConnectionTestQuery("select 1");
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("Hikari-master");

        return dataSource;
    }

    @Bean(name = "slave1DataSource")
    public DataSource slave1DataSource() {

        // 获得连接
        String url = "jdbc:mysql://localhost:33116/test";
        String user = "root";
        String password = "123456";

        // 使用线程池获取连接
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setAutoCommit(true);
        dataSource.setConnectionTestQuery("select 1");
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("Hikari-slave1");

        return dataSource;
    }
}
