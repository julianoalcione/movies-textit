package br.com.textoit.movies.Movies.controller.form;

import br.com.textoit.movies.Movies.model.Movie;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Stream;

@Data
public class MovieForm {
    @NotNull
    private Integer movieYear;
    @NotBlank
    private String title;
    @NotBlank
    private String studios;
    @NotBlank
    private String producers;
    @NotNull
    private Boolean winner;

    public Movie convertToEntity() {
        return new Movie(movieYear, title, studios, producers, winner);
    }
}
