package br.com.textoit.movies.Movies.service;

import br.com.textoit.movies.Movies.model.Movie;
import br.com.textoit.movies.Movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderCsv {

    @Autowired
    private MovieRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        List<Movie> movies = readAndConvertToMovie();
        repository.saveAll(movies);
    }

    public List<Movie> readAndConvertToMovie() {
        ArrayList<Movie> movies = new ArrayList<>();
        String line;
        String separeColumnsBy = ";";

        try {
            BufferedReader csv = new BufferedReader(new FileReader("src/main/resources/csv/movielist.csv"));
            csv.readLine();
            while ((line = csv.readLine()) != null)
                movies.add(convertToMovie(line, separeColumnsBy));
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private Movie convertToMovie(String line, String separeColumnsBy) {
        String[] movieCsv = line.split(separeColumnsBy);
        Integer year = Integer.parseInt(movieCsv[0]);
        String title = movieCsv[1];
        String studios = movieCsv[2];
        String producers = movieCsv[3];
        String winner =  movieCsv.length > 4 ? movieCsv[4] : "no";

        return new Movie(year, title, studios, producers, convertWinnerToBoolean(winner));
    }

    private boolean convertWinnerToBoolean(String winner){
        return winner.trim().equalsIgnoreCase("YES");
    }
}
