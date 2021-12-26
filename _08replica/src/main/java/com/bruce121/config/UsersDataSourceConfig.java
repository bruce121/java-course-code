package com.bruce121.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.XADataSource;

@Configuration
@MapperScan(basePackages = "com.bruce121.mybatis.mapper1", sqlSessionFactoryRef = "usersSqlSessionFactory")
public class UsersDataSourceConfig {

    static {
        // 加载驱动类，会自动执行静态代码块中的内容
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Bean(name = "usersDataSource")
    public XADataSource usersDataSource() {

        // 获得连接
        String url = "jdbc:mysql://localhost:33136/users?pinGlobalTxToPhysicalConnection=true";
        String user = "root";
        String password = "123456";

        // 使用线程池获取连接
        DruidXADataSource dataSource = new DruidXADataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(true);
        dataSource.setMaxActive(10);

        return dataSource;
    }

    @Bean("atomikosDataSource_users")
    public AtomikosDataSourceBean atomikosDataSourceBean(@Qualifier("usersDataSource") XADataSource dataSource) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setBeanName("usersDataSourceBean");
        return atomikosDataSourceBean;
    }

    @Bean("usersSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("atomikosDataSource_users")AtomikosDataSourceBean datasource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(datasource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        factory.setTypeAliasesPackage("com.bruce121");
        factory.setConfiguration(configuration);
        return factory.getObject();
    }
}
