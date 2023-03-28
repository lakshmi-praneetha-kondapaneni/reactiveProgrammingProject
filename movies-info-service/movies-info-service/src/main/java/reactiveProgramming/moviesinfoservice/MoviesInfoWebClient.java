package reactiveProgramming.moviesinfoservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;
import reactor.core.publisher.Mono;

@Component
public class MoviesInfoWebClient implements CommandLineRunner {

    @Override
       public void run(String... args) throws Exception {
        WebClient client = WebClient.create("http://localhost:8080");
           Mono<MovieInfo> moviesInfoMono = client.get()
            .uri("/movieinfos/{id}", "1")
            .retrieve()
            .bodyToMono(MovieInfo.class);
    //System.out.print("");
    moviesInfoMono.subscribe(System.out::println);
   }
}
