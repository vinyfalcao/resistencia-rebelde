package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.repositories.LocalizacaoRebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.testfactories.RebeldeEntityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RebeldeServiceImplTest {


    @Mock
    private RebeldeRepository mockedRebeldeRepository;
    @Mock
    private LocalizacaoRebeldeRepository mockedLocalizacaoRebeldeRepository;
    @InjectMocks
    private RebeldeServiceImpl rebeldeServiceImpl;

    @Test
    public void shouldReturnAllRebeldes(){
        //Preparação(setUp) do teste
        final List<RebeldeEntity>
                expectedRebeldes = Arrays.asList(
                RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build(),
                RebeldeEntityTestFactory.aRebeldeEntity().withId(2L).build(),
                RebeldeEntityTestFactory.aRebeldeEntity().withId(3L).build());
        when(mockedRebeldeRepository.findAll()).thenReturn(expectedRebeldes);
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        var result = rebeldeServiceImpl.findAll();
        assertThat(result).isEqualTo(expectedRebeldes);
    }

    @Test
    public void shouldReturnAnEmptyListWhenRepositoryReturnsEmptyList(){
        //Preparação(setUp) do teste
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
        final Optional<RebeldeEntity> expectedRebelde = Optional.of(RebeldeEntityTestFactory.aRebeldeEntity().withId(EXPECTED_ID).build());
        when(mockedRebeldeRepository.findById(EXPECTED_ID)).thenReturn(expectedRebelde);
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        var result = rebeldeServiceImpl.findById(1L);
        assertThat(result).isEqualTo(expectedRebelde.get());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRebeldeNotFound(){
        //Preparação(setUp) do teste
        when(mockedRebeldeRepository.findById(anyLong())).thenReturn(Optional.empty());
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);
        //Execução
        assertThrows(RuntimeException.class, () -> rebeldeServiceImpl.findById(1L));
    }

    @Test
    public void shouldSaveRebelde(){
        final var expectedRebelde = RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build();
        when(mockedRebeldeRepository.save(expectedRebelde)).thenReturn(expectedRebelde);
        rebeldeServiceImpl = new RebeldeServiceImpl(mockedRebeldeRepository, mockedLocalizacaoRebeldeRepository);

        assertThat(rebeldeServiceImpl.save(expectedRebelde)).isEqualTo(expectedRebelde);
    }

    @Test
    public void whenUpdateLocalizacaoRebeldeShouldUseExistingLocalizacaoIfExists(){
        final var mockedRebelde = RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build();
        when(mockedRebeldeRepository.findById(mockedRebelde.getId())).thenReturn(Optional.of(mockedRebelde));

        final var expectedLocalizacaoRebelde = generateLocalizacaoRebeldeInstance(1L);
        when(mockedLocalizacaoRebeldeRepository.findByNomeGalaxiaAndLatitudeAndLongitude(expectedLocalizacaoRebelde.getNomeGalaxia(),
                expectedLocalizacaoRebelde.getLatitude(), expectedLocalizacaoRebelde.getLongitude()))
                .thenReturn(Optional.of(expectedLocalizacaoRebelde));

        final var localizacaoRequest = generateLocalizacaoRebeldeInstance(null);

        this.rebeldeServiceImpl.updateLocalizacao(mockedRebelde.getId(), localizacaoRequest);
        final var expectedRebelde = RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build();
        expectedRebelde.setLocalizacao(expectedLocalizacaoRebelde);
        verify(this.mockedRebeldeRepository).save(expectedRebelde);
    }

    @Test
    public void whenUpdateLocalizacaoRebeldeShouldUseProvidedLocalizacaoIfNotExists(){
        final var mockedRebelde = RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build();
        when(mockedRebeldeRepository.findById(mockedRebelde.getId())).thenReturn(Optional.of(mockedRebelde));

        when(mockedLocalizacaoRebeldeRepository.findByNomeGalaxiaAndLatitudeAndLongitude(anyString(), anyDouble(), anyDouble()))
                .thenReturn(Optional.empty());

        final var expectedLocalizacaoRebelde = generateLocalizacaoRebeldeInstance(null);

        this.rebeldeServiceImpl.updateLocalizacao(mockedRebelde.getId(), expectedLocalizacaoRebelde);
        final var expectedRebelde = RebeldeEntityTestFactory.aRebeldeEntity()
                .withId(1L)
                .withLocalizacao(expectedLocalizacaoRebelde)
                .build();
        verify(this.mockedRebeldeRepository).save(expectedRebelde);
    }


    private LocalizacaoRebeldeEntity generateLocalizacaoRebeldeInstance(final Long id){
        LocalizacaoRebeldeEntity localizacaoRebeldeEntity = new LocalizacaoRebeldeEntity();
        localizacaoRebeldeEntity.setId(id);
        localizacaoRebeldeEntity.setLatitude(10.0);
        localizacaoRebeldeEntity.setLongitude(-10.0);
        localizacaoRebeldeEntity.setNomeGalaxia("Galaxia 1");
        return localizacaoRebeldeEntity;
    }

}