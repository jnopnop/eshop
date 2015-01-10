package org.nop.eshop.configuration;


import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SessionFactory;
import org.nop.eshop.model.*;
import org.nop.eshop.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@EnableWebMvc
@Configuration
@Import({ SecurityConfig.class })
@ComponentScan(basePackages = "org.nop.eshop")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_DIR_PROVIDER = "hibernate.search.default.directory_provider";
    private static final String PROPERTY_NAME_HIBERNATE_INDEX_DIR = "hibernate.search.default.indexBase";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    @Resource
    private Environment env;

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        return dataSource;
    }


    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN, env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        properties.put(PROPERTY_NAME_HIBERNATE_DIR_PROVIDER, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIR_PROVIDER));
        properties.put(PROPERTY_NAME_HIBERNATE_INDEX_DIR, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_INDEX_DIR));
        return properties;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.addAnnotatedClasses(
                User.class, Role.class, Person.class,
                MoviePersonId.class, MoviePerson.class,
                Movie.class, Genre.class, Country.class,
                Comment.class, News.class, Image.class);
        sessionBuilder.addPackage("org.nop.eshop.model");
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2HttpMessageConverter());
        converters.add(byteArrayHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setPrefixJson(false);
        converter.setPrettyPrint(true);
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/fonts/");
        registry.addResourceHandler("/ckeditor/**").addResourceLocations("/WEB-INF/ckeditor/");
        registry.addResourceHandler("/decorators/**").addResourceLocations("/WEB-INF/decorators/");
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        byteArrayHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return byteArrayHttpMessageConverter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(8);
        pool.setMaxPoolSize(16);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
