package br.com.textoit.movies.Movies.controller.form;

import br.com.textoit.movies.Movies.model.Movie;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MovieUpdateForm {
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String studios;
    @NotNull
    private Boolean winner;

    public Movie convertToEntity() {
        return new Movie(id, null, title, studios, null, winner);
    }
}
