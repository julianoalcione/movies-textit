package br.com.textoit.movies.Movies.controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoviesWinnersResponseDto {
    public MoviesWinnersDto min;
    public MoviesWinnersDto max;
}
