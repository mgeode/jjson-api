package de.mgeo.json.api.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigSwagger {
    @Autowired
    BuildProperties buildProperties;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.any())
                .paths(PathSelectors.regex("(?!/error).+")).paths(PathSelectors.regex("(/api/).+"))
                .build().pathProvider(new RelativePathProvider(null) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/";
                    }
                })
                .apiInfo( metadata() );
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title( buildProperties.getAppTitle() )
                .description( "<div style='position: absolute; left:0; top: 0;background-color: #ccc;height: 60px;border-bottom: 20px solid #efefef;width:100%;margin-bottom:-20em;'></div><div style='margin-top: -35px;'></div>" )
                .version( buildProperties.getVersion() )
                .build();
    }


}