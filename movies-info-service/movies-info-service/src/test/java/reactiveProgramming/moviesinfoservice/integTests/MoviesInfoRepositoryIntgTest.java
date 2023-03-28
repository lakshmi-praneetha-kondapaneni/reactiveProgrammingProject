package reactiveProgramming.moviesinfoservice.integTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;
import reactiveProgramming.moviesinfoservice.repository.MovieInfoRepository;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

@DataMongoTest
@ActiveProfiles("test")
@AutoConfigureWebClient
public class MoviesInfoRepositoryIntgTest {
    @Autowired
    MovieInfoRepository movieInfoRepository;



    @BeforeEach
    void setUp()
    {
        var moviesInfo=List.of(new MovieInfo(null,"Batman Begins",
                2005, List.of("Chritian Bale","Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null,"The Dark knight",
                        2008,List.of("Christian Bale","HeathLedger"),LocalDate.parse("2008-07-18")),
               new MovieInfo(null,"Dark Knight Rises",
                       2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28")));
                movieInfoRepository.saveAll(moviesInfo).blockLast();
    }

    @Test
    void findAllTest()
    {
        var moviesInfoFlux=movieInfoRepository.findAll();
        StepVerifier.create(moviesInfoFlux).expectNextCount(3).verifyComplete();
    }
}
