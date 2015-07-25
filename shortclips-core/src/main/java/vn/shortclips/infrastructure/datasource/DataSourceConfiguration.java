package vn.shortclips.infrastructure.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = { "classpath:config.properties",
		"file:/D:/Dropbox/WS/config.properties" }, ignoreResourceNotFound = true)
@ComponentScan("vn")
@EnableTransactionManagement
public class DataSourceConfiguration {
	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		// HikariDataSource
		System.out.println(env.getProperty("jdbc.url"));
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setMaxTotal(20);
		dataSource.setInitialSize(3);
		return dataSource;
	}

	private Map<String, ?> jpaProperties() {
		Map<String, String> jpaPropertiesMap = new HashMap<String, String>();
		System.out.println(env.getProperty("hibernate.dialect"));
		jpaPropertiesMap.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "create");
		return jpaPropertiesMap;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		bean.setJpaPropertyMap(jpaProperties());
		bean.setJpaDialect(new HibernateJpaDialect());
		// bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		bean.setPackagesToScan("vn");
		bean.setDataSource(dataSource());
		return bean;
	}

	@Bean
	public PlatformTransactionManager jpaPlatformTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(emf);
		return jpaTransactionManager;
	}

	@Bean
	public static PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		PersistenceExceptionTranslationPostProcessor bean = new PersistenceExceptionTranslationPostProcessor();
		return bean;
	}
}
