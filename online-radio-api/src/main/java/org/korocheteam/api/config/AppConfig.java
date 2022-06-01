package org.korocheteam.api.config;

import org.korocheteam.api.models.Genre;
import org.korocheteam.api.security.SecurityConfig;
import org.korocheteam.api.services.AudioService;
import org.korocheteam.api.entities.AudioStream;
import org.korocheteam.api.services.CoverService;
import org.korocheteam.api.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

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

    @Value("${icecast.host}")
    private String icecastHost;

    @Value("${icecast.password}")
    private String icecastPassword;

    @Autowired
    private SongService songService;

    @Autowired
    private CoverService coverService;

    @Bean
    public Map<String, AudioStream> audioStreams() {
        Map<String, AudioStream> streams = new HashMap<>();
        for(Genre genre: Genre.values()) {
            AudioStream audioStream = new AudioStream(icecastHost, icecastPassword, songService, coverService, audioPath, coversPath, genre);
            streams.put(genre.toString().toLowerCase(), audioStream);
        }
        return streams;
    }

    @Bean
    public AudioService audioService(Map<String, AudioStream> audioStreams) {
        AudioService audioService = new AudioService(audioStreams);
        audioService.startStreams();
        return audioService;
    }

}
