package reactiveProgramming.moviesinfoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactiveProgramming.moviesinfoservice.domain.MovieInfo;
import reactiveProgramming.moviesinfoservice.service.MoviesInfoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MoviesInfoService moviesInfoService;
    public MoviesInfoController(MoviesInfoService moviesInfoService)
    {
        this.moviesInfoService=moviesInfoService;
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo)
    {
        return moviesInfoService.addMovieInfo(movieInfo);
    }

    @GetMapping("/movieinfos")
    public Flux<MovieInfo> getAllMovieInfos()
    {
        return moviesInfoService.getAllMovieInfos();
    }
    @GetMapping("/movieinfos/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id)
    {
        return moviesInfoService.getMovieInfoById(id);
    }
    @PutMapping("/movieinfos/{id}")
    public Mono<MovieInfo> getMovieInfoById(@RequestBody @Valid MovieInfo movieInfo, @PathVariable String id)
    {
        return moviesInfoService.updateMovieInfo(movieInfo,id);
    }

    @DeleteMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfoById(@PathVariable String id)
    {
        return moviesInfoService.deleteMovieInfoById(id);
    }
}

