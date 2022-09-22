package br.com.textoit.movies.Movies.controller;

import br.com.textoit.movies.Movies.controller.dto.MovieDto;
import br.com.textoit.movies.Movies.controller.dto.MoviesWinnersDto;
import br.com.textoit.movies.Movies.controller.dto.MoviesWinnersResponseDto;
import br.com.textoit.movies.Movies.controller.form.MovieForm;
import br.com.textoit.movies.Movies.controller.form.MovieUpdateForm;
import br.com.textoit.movies.Movies.model.Movie;
import br.com.textoit.movies.Movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/all")
    public ResponseEntity<Stream<MovieDto>> all(){
        List<Movie> all = service.getAll();
        return ResponseEntity.ok(MovieDto.convertList(all));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getById(@PathVariable Integer id){
        Movie movie = service.getById(id);
        if(movie == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(new MovieDto(movie));
    }

    @PostMapping()
    public ResponseEntity<MovieDto> save(@RequestBody @Valid MovieForm form){
        Movie save = service.save(form.convertToEntity());
        return ResponseEntity.ok(new MovieDto(save));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable Integer id, @RequestBody @Valid MovieUpdateForm form){
        Movie movie = service.getById(id);
        if(movie == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        form.setId(id);
        Movie save = service.patch(form.convertToEntity());
        return ResponseEntity.ok(new MovieDto(save));
    }

    @GetMapping("/winners")
    public ResponseEntity<MoviesWinnersResponseDto> getWinners(){
        MoviesWinnersDto winnerFaster = service.getWinnerFaster();
        MoviesWinnersDto winnerSlower = service.getWinnerSlower();
        MoviesWinnersResponseDto moviesWinnersResponseDto = new MoviesWinnersResponseDto(winnerFaster, winnerSlower);

        return ResponseEntity.ok(moviesWinnersResponseDto);
    }
}
