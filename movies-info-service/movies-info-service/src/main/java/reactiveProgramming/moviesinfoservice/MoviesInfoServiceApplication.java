package reactiveProgramming.moviesinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.OptionalInt;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableMongoRepositories
public class MoviesInfoServiceApplication {

	public static void main(String[] args) {


			SpringApplication.run(MoviesInfoServiceApplication.class, args);
		OptionalInt reduced =
				IntStream.range(1, 4).reduce((a, b) -> a + b);
		System.out.println(reduced);
	}

}
