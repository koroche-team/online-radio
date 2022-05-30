package org.korocheteam.api.config;

import org.korocheteam.api.security.SecurityConfig;
import org.korocheteam.api.services.AudioService;
import org.korocheteam.api.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan("org.korocheteam.api")
// TODO: use profiles
@PropertySource("classpath:application-dev.properties")
@Import({DatabaseConfig.class, SecurityConfig.class})
@EnableSwagger2
public class AppConfig {

    @Value("${audio.path}")
    private String audioPath;

    @Value("${covers.path}")
    private String coversPath;

    @Autowired
    private SongService songService;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public AudioService audioService() {
        AudioService audioService = new AudioService(songService, audioPath, coversPath);
        audioService.updateMusicList();
        audioService.runAudioStream();
        return audioService;
    }

}
