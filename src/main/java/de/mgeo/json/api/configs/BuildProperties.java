package de.mgeo.json.api.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/build.properties")
public class BuildProperties {
    @Value("${java.version}")
    private  String javaVersion;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.title}")
    private String appTitle;

    @Value("${build.number}")
    private String buildNumber;

    @Value("${build.date}")
    private String buildDate;

    /**
     * BUILD-INFO AppProperties
     * @return
     */
    public String getAppTitle() {return appTitle; }
    public String getBuildDate(){return buildDate;}
    public String getBuildNumber() {
        return buildNumber;
    }
    public String getAppVersion() {
        if (appVersion==null) {
            return "X";
        } else {
            return appVersion;
        }
//        return appVersion;
    }
    public String getVersion() {
        String jdkvars = javaVersion.substring(0,2);
        if (buildNumber.equals("")) {
            if(System.getenv("BUILD_NUMBER")!=null)
                buildNumber=System.getenv("BUILD_NUMBER");
        }
        return jdkvars+"."+appVersion+"-"+buildNumber;
    }

}
