package org.korocheteam.api.config;

import org.korocheteam.api.security.SecurityConfig;
import org.korocheteam.api.services.AudioService;
import org.korocheteam.api.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("org.korocheteam.api")
// TODO: use profiles
@PropertySource("classpath:application-dev.properties")
@Import({DatabaseConfig.class, SecurityConfig.class})
public class AppConfig {

    @Value("${audio.path}")
    private String audioPath;

    @Value("${covers.path}")
    private String coversPath;

    @Autowired
    private SongService songService;

    @Bean
    public AudioService audioService() {
        AudioService audioService = new AudioService(songService, audioPath, coversPath);
        audioService.updateMusicList();
        audioService.runAudioStream();
        return audioService;
    }

}
