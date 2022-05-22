package org.korocheteam.api.config;

import org.korocheteam.api.security.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.korocheteam.api")
@PropertySource("classpath:application.properties")
@Import({DatabaseConfig.class, SecurityConfig.class})
public class AppConfig {
}
