package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.repositories.LocalizacaoRebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RebeldeServiceImplTest {

    private RebeldeServiceImpl rebeldeServiceImpl;

    @Test
    public void shouldReturnAllRebeldes(){
        //Preparação(setUp) do teste
        final var mockedRebeldeRepository = mock(RebeldeRepository.class);
        final var mockedLocalizacaoRebeldeRepository = mock(LocalizacaoRebeldeRepository.class);
        final List<RebeldeEntity> expectedRebeldes = Arrays.asList(
                generateRebeldeInstance(1L),
                generateRebeldeInstance(2L),
                generateRebeldeInstance(3L));
        when(mockedRebeldeRepository.findAll()).thenReturn(expectedRebeldes);
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        var result = rebeldeServiceImpl.findAll();
        assertThat(result).isEqualTo(expectedRebeldes);
    }

    @Test
    public void shouldReturnAnEmptyListWhenRepositoryReturnsEmptyList(){
        //Preparação(setUp) do teste
        final var mockedRebeldeRepository = mock(RebeldeRepository.class);
        final var mockedLocalizacaoRebeldeRepository = mock(LocalizacaoRebeldeRepository.class);

        final List<RebeldeEntity> expectedRebeldes = Collections.emptyList();
        when(mockedRebeldeRepository.findAll()).thenReturn(expectedRebeldes);
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        var result = rebeldeServiceImpl.findAll();
        assertThat(result).isEqualTo(expectedRebeldes);
    }

    @Test
    public void shouldReturnExpectedRebeldeById(){
        //Preparação(setUp) do teste
        final long EXPECTED_ID = 1L;
        final var mockedRebeldeRepository = mock(RebeldeRepository.class);
        final var mockedLocalizacaoRebeldeRepository = mock(LocalizacaoRebeldeRepository.class);

        final Optional<RebeldeEntity> expectedRebelde = Optional.of(generateRebeldeInstance(EXPECTED_ID));
        when(mockedRebeldeRepository.findById(EXPECTED_ID)).thenReturn(expectedRebelde);
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        var result = rebeldeServiceImpl.findById(1L);
        assertThat(result).isEqualTo(expectedRebelde.get());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRebeldeNotFound(){
        //Preparação(setUp) do teste
        final var mockedRebeldeRepository = mock(RebeldeRepository.class);
        final var mockedLocalizacaoRebeldeRepository = mock(LocalizacaoRebeldeRepository.class);

        when(mockedRebeldeRepository.findById(anyLong())).thenReturn(Optional.empty());
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        assertThrows(RuntimeException.class, () -> rebeldeServiceImpl.findById(1L));
    }

    @Test
    public void shouldSaveRebelde(){
        final var mockedRebeldeRepository = mock(RebeldeRepository.class);
        final var mockedLocalizacaoRebeldeRepository = mock(LocalizacaoRebeldeRepository.class);

        final var expectedRebelde = generateRebeldeInstance(1L);
        when(mockedRebeldeRepository.save(expectedRebelde)).thenReturn(expectedRebelde);

        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);

        assertThat(rebeldeServiceImpl.save(expectedRebelde)).isEqualTo(expectedRebelde);
    }

    private RebeldeEntity generateRebeldeInstance(final Long id){
        RebeldeEntity rebeldeEntity = new RebeldeEntity();
        rebeldeEntity.setId(id);
        return rebeldeEntity;
    }

}