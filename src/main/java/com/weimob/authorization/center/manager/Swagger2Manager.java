/*
 * ArrrowCtrl Inc., All rights reserved. 2017.
 * Oceanware component, Oceanware is a brand owned by ArrowCtrl.
 * Any use of this component should get permission from ArrowCtrl.
 */
package com.weimob.authorization.center.manager;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
public class Swagger2Manager {

    private String baseScanPackage;

    private String apiTitle;

    private String apiVersion;

    private String apiDescription;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(baseScanPackage)).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(apiTitle).description(apiDescription).version(apiVersion).build();
    }

    public String getBaseScanPackage() {
        return baseScanPackage;
    }

    public void setBaseScanPackage(String baseScanPackage) {
        this.baseScanPackage = baseScanPackage;
    }

    public String getApiTitle() {
        return apiTitle;
    }

    public void setApiTitle(String apiTitle) {
        this.apiTitle = apiTitle;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }

}
