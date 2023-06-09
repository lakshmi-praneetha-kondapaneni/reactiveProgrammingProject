package reactiveProgramming.moviesinfoservice.integTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;
import reactiveProgramming.moviesinfoservice.repository.MovieInfoRepository;

import java.time.LocalDate;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
@ExtendWith(SpringExtension.class)
class MoviesInfoServiceApplicationTests {
	@Autowired
	MovieInfoRepository movieInfoRepository;

	@Autowired
	WebTestClient webTestClient;

	static String MOVIES_INFO_URL="/v1/movieinfos";

	@BeforeEach
	void setUp()
	{
		var moviesInfo= List.of(new MovieInfo(null,"Batman Begins",
						2005, List.of("Chritian Bale","Michael Cane"), LocalDate.parse("2005-06-15")),
				new MovieInfo(null,"The Dark knight",
						2008,List.of("Christian Bale","HeathLedger"),LocalDate.parse("2008-07-18")),
				new MovieInfo(null,"Dark Knight Rises",
						2012,List.of("Christian Bale","Tom Hardy"),LocalDate.parse("2012-07-28")));
		movieInfoRepository.saveAll(moviesInfo).blockLast();
	}


	@Test
	void addMovieInfo() {
		var movieInfo=new MovieInfo(null,"Batman Begins",
				2005, List.of("Chritian Bale","Michael Cane"), LocalDate.parse("2005-06-15"));
		webTestClient.post().
				uri(MOVIES_INFO_URL)
				.bodyValue(movieInfo)
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
	void getAllMovieInfos()
	{
		webTestClient.get()
			.uri(MOVIES_INFO_URL)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(MovieInfo.class)
				.hasSize(3);
	}
	@Test
	void getMovieInfoByIdtest()
	{
		var movieInfoId="abc";
		webTestClient.get().uri(MOVIES_INFO_URL+"/{id}",movieInfoId).
				exchange().expectStatus().
				is2xxSuccessful()
				.expectBody(MovieInfo.class)
				.consumeWith(movieInfoEntityExchangeResult->
				{
					var movieInfo= movieInfoEntityExchangeResult.getResponseBody();
					assertNotNull(movieInfo);
				});
	}
	@Test
	void updateMovieInfoTest()
	{
		var movieInfoId="abc";
		var movieInfo=new MovieInfo(null,
				"Batman2",2007, List.of("Chritian Bale","Michael Cute"),LocalDate.parse("2005-06-15"));
		webTestClient.put().uri(MOVIES_INFO_URL+"/{id}",movieInfoId)
				.bodyValue(movieInfo)
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
