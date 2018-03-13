package org.actech.smart.trader;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.MongosExecutable;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongoCmdOptionsBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.io.IOException;

/**
 * Created by paul on 2018/3/10.
 */
@Configuration
public class UnitTestConfiguration {
    @Profile("dev")
    @Bean
    public MongoClient embeddedMongoClient() throws IOException {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        IMongodConfig config = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .cmdOptions(new MongoCmdOptionsBuilder().useNoJournal(false).build())
                .shardServer(true)
                .build();

        MongodExecutable mongodExe = runtime.prepare(config);
        mongodExe.start();

        MongoClient mongo = new MongoClient(config.net().getServerAddress().getHostName(),
                config.net().getPort());
        return mongo;
    }

/*    static {
        LoggerContext loggerContext = ((Logger) LoggerFactory.getLogger(UnitTestConfiguration.class))
                .getLoggerContext();
        loggerContext.reset();
        new BasicConfigurator().configure(loggerContext);
    }*/
}
