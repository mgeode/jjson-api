package de.mgeo.json.api.configs;

import de.mgeo.json.api.Utils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    //@Value("${app.data.dir}")
    private static String dataDir="/tmp";

    //@Value("${app.data.file}")
    private static String dataFile="data-entries.json";


    @Autowired
    Environment environment;

    /**
     * ENVs
     *
     */
    public org.apache.logging.log4j.Logger getLoggerInfo() { return LogManager.getLogger(this); }
    public Environment getEnvironment() { return environment; }
    public Utils getUtils() { return new Utils(); }

    public AppProperties(){
        //DATA_FILE
        if (System.getProperty("DATA_FILE")!=null) {
            this.dataFile=System.getProperty("DATA_FILE");
        }
        else if (System.getenv("DATA_FILE")!=null) {
            this.dataFile=System.getenv("DATA_FILE");
        }

        //DATA_DIR
        if (System.getProperty("DATA_DIR")!=null) {
            this.dataDir=System.getProperty("DATA_DIR");
        }
        else if (System.getenv("DATA_DIR")!=null) {
            this.dataDir=System.getenv("DATA_DIR");
        }
    }

    /**
     * SETTER & GETTERS
     *
     */
    public void setDataDir(String dirname) {
        System.setProperty("DATA_DIR",dirname);
        this.dataDir=dirname;
    }
    public void setDataFile(String filename) {
        System.setProperty("DATA_FILE",filename);
        this.dataFile=filename;
    }

    public static String getDataDir(){return dataDir;}
    public static String getDataFile(){return dataFile;}
    public static String getDataFilePath(){ return getDataDir()+"/"+ getDataFile(); }
}
