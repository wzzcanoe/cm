//package com.ma.cm;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import com.alibaba.druid.pool.DruidDataSource;
//
//@Configuration
//public class MybatisConfig {
//
//
//    @Autowired
//    private DataSourceProperties dataSourceProperties;
//
//
//    @Bean(name = "dataSource")
//    public DataSource dataSource() {
//
//        DruidDataSource dataSource = new DruidDataSource();
////        dataSource.setUrl(dataSourceProperties.getUrl());
////        System.out.println(dataSourceProperties.getUrl());
////        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
////        dataSource.setUsername(dataSourceProperties.getUsername());
////        dataSource.setPassword(dataSourceProperties.getPassword());
//        
//        
//        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
//        .addScript(dataSourceProperties.getSchema())
//        .addScript(dataSourceProperties.getData()).build();
//
//        //return dataSource;
//
//    }
//
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        return sqlSessionFactoryBean.getObject();
//    }
//}