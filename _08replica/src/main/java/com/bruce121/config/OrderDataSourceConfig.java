package com.bruce121.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.XADataSource;
import javax.transaction.UserTransaction;

@Configuration
@MapperScan(basePackages = "com.bruce121.mybatis.mapper", sqlSessionFactoryRef = "orderSqlSessionFactory")
public class OrderDataSourceConfig {

    static {
        // 加载驱动类，会自动执行静态代码块中的内容
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Bean(name = "shardingDbDataSource")
    public XADataSource masterDataSource() {

        // 获得连接
        String url = "jdbc:mysql://localhost:3307/sharding_db?pinGlobalTxToPhysicalConnection=true";
        String user = "root";
        String password = "root";

        // 使用线程池获取连接
        DruidXADataSource dataSource = new DruidXADataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(true);
        dataSource.setMaxActive(10);

        return dataSource;
    }

    @Primary
    @Bean("atomikosDataSource_order")
    public AtomikosDataSourceBean atomikosDataSourceBean(@Qualifier("shardingDbDataSource") XADataSource dataSource) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setBeanName("orderDataSourceBean");
        return atomikosDataSourceBean;
    }

    @Primary
    @Bean("orderSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("atomikosDataSource_order") AtomikosDataSourceBean datasource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(datasource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        factory.setTypeAliasesPackage("com.bruce121");
        factory.setConfiguration(configuration);
        return factory.getObject();
    }

    @Bean
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
