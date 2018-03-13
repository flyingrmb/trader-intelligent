package org.actech.smart.trader;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by paul on 2018/3/9.
 */
@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class Application {
    @Bean
    @Profile("default")
    public MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

    @Component
    @ConfigurationProperties("mongo.db")
    class ConfigurableMongoDbFactory {
        private final Log logger = LogFactory.getLog(ConfigurableMongoDbFactory.class);

        @NotNull
        private String databaseName;

        @Autowired
        private MongoClient mongoClient;

        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

        public MongoDbFactory getFactory() {
            logger.info("Mongo数据库：" + databaseName);
            return new SimpleMongoDbFactory(mongoClient, databaseName);
        }
    }

    @Bean
    public MongoDbFactory mongoDbFactory(ConfigurableMongoDbFactory configurable) {
        return configurable.getFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
