package com.ht.bim;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;

/**
 * Created by zhong on 2018/1/3.
 */
@Configuration
@ImportResource(locations = {"classpath:spring-service.xml"})
public class AppConfig {

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.enableNaming();
                TomcatEmbeddedServletContainer container =
                        super.getTomcatEmbeddedServletContainer(tomcat);
                for (Container child : container.getTomcat().getHost().findChildren()) {
                    if (child instanceof Context) {
                        ClassLoader contextClassLoader = ((Context) child).getLoader().getClassLoader();
                        Thread.currentThread().setContextClassLoader(contextClassLoader);
                        break;
                    }
                }
                return container;
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setType(DataSource.class.getName());
                resource.setName("dataSource");
                resource.setProperty("factory", "com.alibaba.druid.pool.DruidDataSourceFactory");
                resource.setProperty("driverClassName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                resource.setProperty("maxActive", "50");
                resource.setProperty("maxWait", "10000");
                resource.setProperty("removeabandoned", "true");
                resource.setProperty("removeabandonedtimeout", "60");
                resource.setProperty("logabandoned", "false");
                resource.setProperty("filters", "stat");
                resource.setProperty("validationQuery", "select 1");
                resource.setProperty("url",
                        "jdbc:sqlserver://" + System.getProperty("DB_SERVER") + ";DatabaseName=" +
                                System.getProperty("DB"));
                resource.setProperty("username", System.getProperty("DB_USER"));
                resource.setProperty("password", System.getProperty("DB_PWD"));
                context.getNamingResources().addResource(resource);
            }
        };
    }
}
