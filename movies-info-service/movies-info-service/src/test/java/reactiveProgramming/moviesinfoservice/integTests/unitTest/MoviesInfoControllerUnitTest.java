package reactiveProgramming.moviesinfoservice.integTests.unitTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactiveProgramming.moviesinfoservice.controller.MoviesInfoController;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;
import reactiveProgramming.moviesinfoservice.service.MoviesInfoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;

@WebFluxTest(controllers = MoviesInfoController.class)
@AutoConfigureWebTestClient
public class MoviesInfoControllerUnitTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MoviesInfoService moviesInfoService;
    static String MOVIES_INFO_URL="/v1/movieinfos";

    @Test
    void getAllMoviesInfo()
    {

        var moviesInfo= List.of(new MovieInfo(null,"Batman Begins",
                        2005, List.of("Chritian Bale","Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null,"The Dark knight",
                        2008,List.of("Christian Bale","HeathLedger"),LocalDate.parse("2008-07-18")),
                new MovieInfo(null,"Dark Knight Rises",
                        2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28")));
        Mockito.when(moviesInfoService.getAllMovieInfos()).thenReturn(Flux.fromIterable(moviesInfo));
        webTestClient.get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }
    @Test
    void getMovieInfoById()
    {
        var movieInfoId="abc";
        var moviesInfo=new MovieInfo("abc","Dark Knight Rises",
                2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28"));
        Mockito.when(moviesInfoService.getMovieInfoById(movieInfoId)).thenReturn(Mono.just(moviesInfo));
        webTestClient.get().uri(MOVIES_INFO_URL+"/{id}",movieInfoId).
                exchange().expectStatus().
                is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult->
                {
                    var movieInfo= movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(movieInfo);
                    assertEquals(movieInfo.getMovieInfoId(),"abc");
                });
    }

    @Test
    void addMovieInfo()
    {
        var moviesInfo=new MovieInfo(null,"Dark Knight Rises",
                2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28"));
        Mockito.when(moviesInfoService.addMovieInfo(isA(MovieInfo.class))).
                thenReturn(Mono.just(new MovieInfo("abc","Dark Knight Rises",
                        2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28"))));
        webTestClient.post().
                uri(MOVIES_INFO_URL)
                .bodyValue(moviesInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult ->{
                    var savedMovieInfo=movieInfoEntityExchangeResult.getResponseBody();
                    assert savedMovieInfo!=null;
                    assert savedMovieInfo.getMovieInfoId()!=null;

                });
    }
    @Test
    void updateMovieInfo()
    {
        var movieInfoId="abc";
        var moviesInfo=new MovieInfo(null,"Dark Knight Rises",
                2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28"));
        Mockito.when(moviesInfoService.updateMovieInfo(isA(MovieInfo.class),Mockito.anyString()))
                        .thenReturn(Mono.just(
                                new MovieInfo(movieInfoId,"Dark Knight Rises",
                                        2007,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28"))
                        ));
        webTestClient.put().uri(MOVIES_INFO_URL+"/{id}",movieInfoId)
                .bodyValue(moviesInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var updatedMovieInfo=movieInfoEntityExchangeResult.getResponseBody();
                    assert updatedMovieInfo!=null;
                    assert updatedMovieInfo.getMovieInfoId()!=null;
                    assert updatedMovieInfo.getYear()==2007;
                });

    }
    @Test
    void deleteMovieInfoByIdtest()
    {
        var movieInfoId="abc";
        Mockito.when(moviesInfoService.deleteMovieInfoById(movieInfoId))
                        .thenReturn(Mono.empty());
        webTestClient.get().uri(MOVIES_INFO_URL+"/{id}",movieInfoId).
                exchange().expectStatus().
                is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult->
                {
                    var movieInfo= movieInfoEntityExchangeResult.getResponseBody();
                    assertNull(movieInfo);
                });
    }

}
