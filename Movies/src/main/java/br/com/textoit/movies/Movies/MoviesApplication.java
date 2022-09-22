package br.com.textoit.movies.Movies;

import br.com.textoit.movies.Movies.repository.MovieRepository;
import br.com.textoit.movies.Movies.service.MovieService;
import br.com.textoit.movies.Movies.service.ReaderCsv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}
}
