package reactiveProgramming.moviesinfoservice.service;

import org.springframework.stereotype.Service;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;
import reactiveProgramming.moviesinfoservice.repository.MovieInfoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesInfoService {

    private MovieInfoRepository movieInfoRepository;
    public MoviesInfoService(MovieInfoRepository movieInfoRepository)
    {
        this.movieInfoRepository=movieInfoRepository;
    }
    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {

        return movieInfoRepository.save(movieInfo);
    }
    public Flux<MovieInfo> getAllMovieInfos()
    {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieInfoById(String id) {
        return movieInfoRepository.findById(id);
    }

    public Mono<MovieInfo> updateMovieInfo(MovieInfo updatedmovieInfo, String id) {
      movieInfoRepository.findById(id).flatMap(movieInfo->{
          movieInfo.setCast(updatedmovieInfo.getCast());
         movieInfo.setName(updatedmovieInfo.getName());
         movieInfo.setYear(updatedmovieInfo.getYear());
         movieInfo.setReleaseDate(updatedmovieInfo.getReleaseDate());
         return movieInfoRepository.save(movieInfo);
      });
        return null;
    }

    public Mono<Void> deleteMovieInfoById(String id) {
        return movieInfoRepository.deleteById(id);
    }
}
