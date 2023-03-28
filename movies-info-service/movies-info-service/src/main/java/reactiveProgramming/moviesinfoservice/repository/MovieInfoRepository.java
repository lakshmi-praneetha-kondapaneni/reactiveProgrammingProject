package reactiveProgramming.moviesinfoservice.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;


public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo,String> {

}
