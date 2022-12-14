package br.com.textoit.movies.Movies.service;

import br.com.textoit.movies.Movies.controller.dto.MoviesWinnersDto;
import br.com.textoit.movies.Movies.model.Movie;
import br.com.textoit.movies.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    public MovieRepository repository;

    public MoviesWinnersDto getWinnerFaster(){
        List<Movie> allWinners = repository.getAllWinners();

        Movie movieWithSmallerDiferenceOne = new Movie();
        Movie movieWithSmallerDiferenceTwo = new Movie();
        Integer smallerDiferenceFound = Integer.MAX_VALUE;

        for (Movie movie: allWinners){
            for (Movie movieCompare: allWinners){
                boolean isSameProducer = isSameProducers(movie, movieCompare);
                boolean isSameMovie = movie.getId().equals(movieCompare.getId());

                if(isSameProducer && !isSameMovie) {
                    int diference = getDiferenceMovieYear(movie, movieCompare);
                    if(diference < smallerDiferenceFound){
                        smallerDiferenceFound = diference;
                        movieWithSmallerDiferenceOne = movie;
                        movieWithSmallerDiferenceTwo = movieCompare;
                    }
                }
            }
        }

        if(smallerDiferenceFound == Integer.MAX_VALUE)
            return null;

        Integer previous;
        Integer following;

        if(movieWithSmallerDiferenceOne.getMovieYear() > movieWithSmallerDiferenceTwo.getMovieYear()){
            previous = movieWithSmallerDiferenceTwo.getMovieYear();
            following = movieWithSmallerDiferenceOne.getMovieYear();
        }else{
            previous = movieWithSmallerDiferenceOne.getMovieYear();
            following = movieWithSmallerDiferenceTwo.getMovieYear();
        }

        return new MoviesWinnersDto(movieWithSmallerDiferenceOne.getProducers(), smallerDiferenceFound, previous, following);
    }

    public MoviesWinnersDto getWinnerSlower(){
        List<Movie> allWinners = repository.getAllWinners();

        Movie movieWithBiggerDiferenceOne = new Movie();
        Movie movieWithBiggerDiferenceTwo = new Movie();
        Integer biggerDiferenceFound = Integer.MIN_VALUE;

        for (Movie movie: allWinners){
            for (Movie movieCompare: allWinners){
                boolean isSameProducer = isSameProducers(movie, movieCompare);
                boolean isSameMovie = movie.getId().equals(movieCompare.getId());

                if(isSameProducer && !isSameMovie) {
                    int diference = getDiferenceMovieYear(movie, movieCompare);

                    if(diference > biggerDiferenceFound){
                        biggerDiferenceFound = diference;
                        movieWithBiggerDiferenceOne = movie;
                        movieWithBiggerDiferenceTwo = movieCompare;
                    }
                }
            }
        }

        if(biggerDiferenceFound == Integer.MIN_VALUE)
            return null;

        Integer previous;
        Integer following;

        if(movieWithBiggerDiferenceOne.getMovieYear() > movieWithBiggerDiferenceTwo.getMovieYear()){
            previous = movieWithBiggerDiferenceTwo.getMovieYear();
            following = movieWithBiggerDiferenceOne.getMovieYear();
        }else{
            previous = movieWithBiggerDiferenceTwo.getMovieYear();
            following = movieWithBiggerDiferenceOne.getMovieYear();
        }

        return new MoviesWinnersDto(movieWithBiggerDiferenceOne.getProducers(), biggerDiferenceFound, previous, following);
    }


    private boolean isSameProducers(Movie movie, Movie movieCompare) {
        return movie.getProducers().toLowerCase().trim().contains(movieCompare.getProducers().trim().toLowerCase());
    }

    private int getDiferenceMovieYear(Movie movie, Movie movieCompare) {
        if (movie.getMovieYear() > movieCompare.getMovieYear())
            return movie.getMovieYear() - movieCompare.getMovieYear();

        return movieCompare.getMovieYear() - movie.getMovieYear();
    }

    public List<Movie> getAll(){
        return repository.findAll();
    }

    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    public Movie patch(Movie movieUpdate) {
        Movie movie = repository.findById(movieUpdate.getId()).get();

        movie.setTitle(movieUpdate.getTitle());
        movie.setStudios(movieUpdate.getStudios());
        movie.setWinner(movieUpdate.getWinner());

        return save(movie);
    }

    public Movie getById(Integer id) {
        Optional<Movie> movie = repository.findById(id);
        if(movie.isPresent())
            return movie.get();

        return null;
    }
}
