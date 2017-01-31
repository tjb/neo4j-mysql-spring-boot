package com.example;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.example.graph")
@EnableJpaRepositories(basePackages = "com.example.relational", transactionManagerRef = "mysqlTransactionManager")
@EnableTransactionManagement
public class DatasourceConfig extends Neo4jConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(DatasourceConfig.class);

  @Bean
  public org.neo4j.ogm.config.Configuration getConfiguration() {
    org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
    config
        .driverConfiguration()
        .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
        .setURI("http://neo4j:root@localhost:7474");
    return config;
  }

  @Bean
  public SessionFactory getSessionFactory() {
    return new SessionFactory(getConfiguration(), "com.example.graph");
  }

  @Bean
  public Session getSession() throws Exception {
    return super.getSession();
  }

  @Primary
  @Bean(name = "dataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder
        .create()
        .driverClassName("com.mysql.jdbc.Driver")
        .build();
  }

  @Primary
  @Bean
  @Autowired
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(dataSource);
    entityManagerFactory.setPackagesToScan("com.example.relational");
    entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
    Map<String, String> jpaProperties = new HashMap<>();
    jpaProperties.put("hibernate.connection.charSet", "UTF-8");
    jpaProperties.put("spring.jpa.hibernate.ddl-auto", "none");
    jpaProperties.put("spring.jpa.hibernate.naming-strategy", "org.springframework.boot.orm.jpa.SpringNamingStrategy");
    jpaProperties.put("hibernate.bytecode.provider", "javassist");
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
    jpaProperties.put("hibernate.hbm2ddl.auto", "none");
    jpaProperties.put("hibernate.order_inserts", "true");
    jpaProperties.put("hibernate.jdbc.batch_size", "50");

    entityManagerFactory.setJpaPropertyMap(jpaProperties);
    entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
    return entityManagerFactory;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Autowired
  @Bean(name = "neo4jTransactionManager")
  public Neo4jTransactionManager neo4jTransactionManager(Session sessionFactory) {
    return new Neo4jTransactionManager(sessionFactory);
  }

  @Autowired
  @Primary
  @Bean(name = "mysqlTransactionManager")
  public JpaTransactionManager mysqlTransactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory)
      throws Exception {
    return new JpaTransactionManager(entityManagerFactory.getObject());
  }


  @Autowired
  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager(Neo4jTransactionManager neo4jTransactionManager,
                                                       JpaTransactionManager mysqlTransactionManager) {
    return new ChainedTransactionManager(
        mysqlTransactionManager,
        neo4jTransactionManager
    );
  }
}