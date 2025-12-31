package jpabook.jpashop.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title       = "Jpa Shop Backend API",
                version     = "1.0.0",
                description = "API Description"
        )
//        servers = {
//                @Server(
//                        url         = "https://api.boh-server.p-e.kr",
//                        description = "Production"
//                )
//        }
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI().info(new Info()
                        .title("Jpa Shop Backend API")
                        .description("API Description")
                        .version("1.0.0")
                );
    }
}
