package mx.uady.sicei.kardex_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "mx.uady.sicei.kardex_service.repositories" })
@EnableJpaAuditing
public class JPAConfiguration {
}
