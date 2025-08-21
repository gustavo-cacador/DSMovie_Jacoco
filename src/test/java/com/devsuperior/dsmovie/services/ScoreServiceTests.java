package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService scoreService;

	@Mock
	private UserService userService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ScoreRepository scoreRepository;

	private long existingMovieId;
	private long nonExistingMovieId;

	private MovieEntity movie;
	private UserEntity user;
	private ScoreDTO scoreDTO;

	@BeforeEach
	void setUp() throws Exception {

		existingMovieId = 1L;
		nonExistingMovieId = 2L;


		movie = MovieFactory.createMovieEntity();
		movie.setId(existingMovieId);

		user = UserFactory.createClientUser();

		ScoreEntity score = ScoreFactory.createScoreEntity();
		scoreDTO = new ScoreDTO(existingMovieId, score.getValue());

		// Mock user autenticado
		Mockito.when(userService.authenticated()).thenReturn(user);

		// Mock movie existente
		Mockito.when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movie));

		// Mock movie inexistente
		Mockito.when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());

		// Mock salvar score
		Mockito.when(scoreRepository.saveAndFlush(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

		// Mock salvar movie
		Mockito.when(movieRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
	}

	@Test public void saveScoreShouldReturnMovieDTO() {

		MovieDTO result = scoreService.saveScore(scoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), movie.getId());
		Assertions.assertEquals(result.getScore(), movie.getScore());
		Assertions.assertEquals(result.getCount(), movie.getCount()); }
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {

		ScoreDTO dto = new ScoreDTO(nonExistingMovieId, 5.0);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			scoreService.saveScore(dto);
		});

		Mockito.verify(movieRepository).findById(nonExistingMovieId);
	}
}
