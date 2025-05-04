package mx.uady.sicei.kardex_service.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import static mx.uady.sicei.kardex_service.configuration.Constants.OAUTH2_SECURITY_SCHEME;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KardexServiceOpenAPIConfiguration {
    private static final String APPLICATION_NAME = "Kardex service";

    private static final String APPLICATION_DESCRIPTION = "Aplicativo que incluye las acciones disponibles para gestionar la información de alumnos, asignaturas y calificaciones";

    private static final String APPLICATION_VERSION = KardexServiceOpenAPIConfiguration.class
            .getPackage()
            .getImplementationVersion();

    @Bean
    OpenAPI kardexServiceAPI() {
        Info apiInformation = new Info()
                .title(APPLICATION_NAME)
                .version("v" + APPLICATION_VERSION)
                .description(APPLICATION_DESCRIPTION);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(OAUTH2_SECURITY_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(OAUTH2_SECURITY_SCHEME, new SecurityScheme()
                                .flows(new OAuthFlows()
                                        .clientCredentials(new OAuthFlow()
                                                .tokenUrl("https://sicei.us.auth0.com/oauth/token")))
                                .type(SecurityScheme.Type.OAUTH2)))
                .info(apiInformation);
    }
}