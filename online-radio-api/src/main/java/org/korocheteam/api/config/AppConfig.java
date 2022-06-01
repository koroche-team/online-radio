package org.korocheteam.api.config;

import org.korocheteam.api.models.Song;
import org.korocheteam.api.security.SecurityConfig;
import org.korocheteam.api.services.AudioService;
import org.korocheteam.api.entities.AudioStream;
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
@EnableSwagger2
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

    // TODO: fix swagger page
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Map<Song.Genre, AudioStream> audioStreams() {
        Map<Song.Genre, AudioStream> streams = new HashMap<>();
        for(Song.Genre genre: Song.Genre.values()) {
            AudioStream audioStream = new AudioStream(icecastHost, icecastPassword, songService, audioPath, coversPath, genre);
            streams.put(genre, audioStream);
        }
        return streams;
    }

    @Bean
    public AudioService audioService(Map<Song.Genre, AudioStream> audioStreams) {
        AudioService audioService = new AudioService(audioStreams);
        audioService.startStreams();
        return audioService;
    }

}
