package br.com.textoit.movies.Movies.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MoviesWinnersDto {
    public String producer;
    public Integer interval;
    public Integer previousWin;
    public Integer followingWin;
}
