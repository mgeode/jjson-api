package de.mgeo.json.api;


import de.mgeo.json.api.configs.AppProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@SpringBootApplication
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static org.apache.logging.log4j.Logger logInfo = LogManager.getLogger(Application.class);


    public static void main(String[] args) {
        if (appIsRunnable()) {
            SpringApplication.run(Application.class, args);
            logger.info("Loglevel '" + logInfo.getLevel() + "' is set");
        }
    }

    private static boolean appIsRunnable() {
        AppProperties configApp = new AppProperties();
        File dataFile = new File(configApp.getDataFilePath());
        if (dataFile.exists()) {
            return true;
        } else {
            File dataDir = new File(dataFile.getParent());
            try {
                FileUtils.mkdir(dataDir, true);
                FileWriter initWriter = new FileWriter(configApp.getDataFilePath());
                initWriter.write("{}");
                initWriter.close();
            } catch (IOException e) {
                logger.error("Init-process can't creating Data-JSON!");
                logger.error(e.getMessage());
                return false;
            }
        }
        return true;
    }
}

