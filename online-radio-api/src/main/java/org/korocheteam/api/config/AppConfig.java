package org.korocheteam.api.config;

import org.korocheteam.api.security.SecurityConfig;
import org.korocheteam.api.services.AudioService;
import org.korocheteam.api.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.korocheteam.api")
@PropertySource("classpath:application.properties")
@Import({DatabaseConfig.class, SecurityConfig.class})
public class AppConfig {

    @Autowired
    private SongService songService;

    @Bean
    public AudioService audioService() {
        AudioService audioService = new AudioService(songService);
        audioService.runAudioStream();
        return audioService;
    }

}
