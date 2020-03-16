package de.mgeo.json.api.controllers;


import de.mgeo.json.api.configs.BuildProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Return versionnumber
 */

@RestController
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    BuildProperties buildProperties;

    /* GET HOME */
    @RequestMapping(method = RequestMethod.GET, value="/")
    public String getWelcome() {

        StringBuilder strb = new StringBuilder();
        strb.append("<html><head><title>"+ buildProperties.getAppTitle()+"-"+ buildProperties.getVersion() +"</title></head><body style='font-family: arial,helvetica,sans-serif;'>");
        strb.append("<center style='margin-top: 10%;'>");
        strb.append("<div style='font-size: 250%;color:#ccc;'>"+ buildProperties.getAppTitle()+"</div>");
        strb.append("<div style='font-size: 180%;opacity:0.7;margin-top: -0.6em;font-weight:bold;letter-spacing:1px;'><i>v"+ buildProperties.getVersion()+"</i></div>");
        strb.append("<div style='font-size: 11px;'>"+ buildProperties.getBuildDate()+"</div>");
        strb.append("</center></body></html>");

        return strb.toString();
    }

    @RequestMapping ({"/docs", "/docs/"})
    public String docs2Swagger() {
        StringBuilder strb = new StringBuilder();
        strb.append("<html><head>");
        strb.append("<meta http-equiv=\"refresh\" content=\"0; URL=/swagger-ui.html\">");
        strb.append("</head><body></body></html>");
        return strb.toString();
    }

    /* GET VERSION */
    @GetMapping({"/version","/healthz"})
    public String getVersion() {
        return buildProperties.getVersion().trim();
    }

}
