package com.mugane.MakMuGaNeTalk.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger2Configuration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("v1-definition")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info().title("MakMuGaNeTalk API Documentation")
                .description("토이 프로젝트 MakMuGaNe Talk API 문서 입니다.")
                .version("v1.0.0"));
    }

}
