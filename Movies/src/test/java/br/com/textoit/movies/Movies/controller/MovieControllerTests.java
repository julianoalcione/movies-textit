package br.com.textoit.movies.Movies.controller;

import br.com.textoit.movies.Movies.controller.dto.MoviesWinnersResponseDto;
import br.com.textoit.movies.Movies.model.Movie;
import br.com.textoit.movies.Movies.repository.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class MovieControllerTests {
    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Movie> movies = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        movies.addAll(Arrays.asList(
            new Movie(1, 1980, "tom e jerry 1", "Associated Film Distribution", "Allan Carr", true),
            new Movie(2, 1985,"tom e jerry 2", "United Artists","Allan Carr",true),
            new Movie(3, 2010,"tropa de elite 1","MGM, United Artists","Steve Shagan",true),
            new Movie(4, 2020,"tropa de elite 2","MGM, United Artists","Steve Shagan",true),
            new Movie(5, 1980,"Friday the 13th","Paramount Pictures","Sean S. Cunningham",true),
            new Movie(6, 1989,"Friday the 14th","Paramount Pictures","Sean S. Cunningham",true),
            new Movie(7, 1985,"The Nude Bomb","Universal Studios","Jennings Lang",true),
            new Movie(8, 1995,"The Nude Bomb 2","Universal Studios","Jennings Lang",true)
        ));
    }

    @Test
    public void shouldBeOk() throws Exception {
        Mockito.when(movieRepository.getAllWinners()).thenReturn(movies);

        MvcResult result = mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andReturn();

        MoviesWinnersResponseDto moviesWinnersResponseDto = parseResponseToWinnerResponseDto(result);

        assertThat(moviesWinnersResponseDto.getMin().getProducer()).isEqualTo(movies.get(0).getProducers());
        assertThat(moviesWinnersResponseDto.getMax().getProducer()).isEqualTo(movies.get(2).getProducers());
    }

    @Test
    public void shouldReturnNullWhenAnyoneHasTwoMovies() throws Exception {

        List winnersWitoutTwoMovies = Arrays.asList(
                new Movie(2, 1985,"tom e jerry 2", "United Artists","Allan Carr",true),
                new Movie(3, 2010,"tropa de elite 1","MGM, United Artists","Steve Shagan",true),
                new Movie(6, 1989,"Friday the 14th","Paramount Pictures","Sean S. Cunningham",true),
                new Movie(7, 1985,"The Nude Bomb","Universal Studios","Jennings Lang",true)
        );

        Mockito.when(movieRepository.getAllWinners()).thenReturn(winnersWitoutTwoMovies);

        MvcResult result = mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andReturn();

        MoviesWinnersResponseDto moviesWinnersResponseDto = parseResponseToWinnerResponseDto(result);

        assertThat(moviesWinnersResponseDto.getMin()).isNull();
        assertThat(moviesWinnersResponseDto.getMax()).isNull();
    }

    @Test
    public void shouldReturnNullWhenNotHaveMovie() throws Exception {
        movies = new ArrayList<>();
        Mockito.when(movieRepository.getAllWinners()).thenReturn(movies);

        MvcResult result = mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andReturn();

        MoviesWinnersResponseDto moviesWinnersResponseDto = parseResponseToWinnerResponseDto(result);

        assertThat(moviesWinnersResponseDto.getMin()).isNull();
        assertThat(moviesWinnersResponseDto.getMax()).isNull();
    }

    private MoviesWinnersResponseDto parseResponseToWinnerResponseDto(MvcResult result) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(result.getResponse().getContentAsString());// response will be the json String
        return gson.fromJson(object, MoviesWinnersResponseDto.class);
    }
}
