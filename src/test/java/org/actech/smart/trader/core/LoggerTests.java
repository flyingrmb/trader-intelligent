package org.actech.smart.trader.core;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;

/**
 * Created by paul on 2018/3/10.
 */
public class LoggerTests {
    private static final Log logger = LogFactory.getLog(JobLauncherCommandLineRunner.class);

    @Before
    public void resetLogging() {
        LoggerContext loggerContext = ((Logger)LoggerFactory.getLogger(getClass())).getLoggerContext();
        loggerContext.reset();
        new BasicConfigurator().configure(loggerContext);
    }

    @Test
    public void printLoggerWithPriority() {
        logger.info("This is the logger printed in INFO priority");
        logger.debug("This is the logger printed in DEBUG priority");
        logger.warn("This is the logger printed in WARN priority");
        logger.error("This is the logger printed in ERROR priority");
    }
}
