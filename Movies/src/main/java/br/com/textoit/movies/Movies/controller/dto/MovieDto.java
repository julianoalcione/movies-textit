package br.com.textoit.movies.Movies.controller.dto;

import br.com.textoit.movies.Movies.model.Movie;
import lombok.Data;

import java.util.List;
import java.util.stream.Stream;

@Data
public class MovieDto {
    private Integer id;
    private Integer movieYear;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.movieYear = movie.getMovieYear();
        this.title = movie.getTitle();
        this.studios = movie.getStudios();
        this.producers = movie.getProducers();
        this.winner = movie.getWinner();
    }

    public static Stream<MovieDto> convertList(List<Movie> movies){
        return movies.stream().map(MovieDto::new);
    }
}
