package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.repositories.ItemInventarioRepository;
import br.com.starwars.resistenciarebelde.repositories.LocalizacaoRebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.testfactories.RebeldeEntityTestFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RebeldeServiceImplTest {


    @Mock
    private RebeldeRepository mockedRebeldeRepository;
    @Mock
    private LocalizacaoRebeldeRepository mockedLocalizacaoRebeldeRepository;
    @Mock
    private ItemInventarioRepository itemInventarioRepository;
    @InjectMocks
    private RebeldeServiceImpl rebeldeServiceImpl;



    @Test
    public void shouldReturnAllRebeldes() throws ExecutionException, InterruptedException {
        //Preparação(setUp) do teste
        final List<RebeldeEntity>
                expectedRebeldes = Arrays.asList(
                RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build(),
                RebeldeEntityTestFactory.aRebeldeEntity().withId(2L).build(),
                RebeldeEntityTestFactory.aRebeldeEntity().withId(3L).build());
        when(mockedRebeldeRepository.findAllBy()).thenReturn(CompletableFuture.completedFuture(expectedRebeldes));
        //Execução
        var result = rebeldeServiceImpl.findAll().get();
        assertThat(result).isEqualTo(expectedRebeldes);
    }

    @Test
    public void shouldReturnAnEmptyListWhenRepositoryReturnsEmptyList() throws ExecutionException, InterruptedException {
        //Preparação(setUp) do teste
        final List<RebeldeEntity> expectedRebeldes = Collections.emptyList();
        when(mockedRebeldeRepository.findAllBy()).thenReturn(CompletableFuture.completedFuture(expectedRebeldes));
        //Execução
        var result = rebeldeServiceImpl.findAll().get();
        assertThat(result).isEqualTo(expectedRebeldes);
    }

    @Test
    public void shouldReturnExpectedRebeldeById() throws ExecutionException, InterruptedException {
        //Preparação(setUp) do teste
        final long EXPECTED_ID = 1L;
        final Optional<RebeldeEntity> expectedRebelde = Optional.of(RebeldeEntityTestFactory.aRebeldeEntity().withId(EXPECTED_ID).build());
        when(mockedRebeldeRepository.findById(EXPECTED_ID)).thenReturn(expectedRebelde);
        //Execução
        var result = rebeldeServiceImpl.findById(1L).get();
        assertThat(result).isEqualTo(expectedRebelde.get());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRebeldeNotFound(){
        //Preparação(setUp) do teste
        when(mockedRebeldeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //Execução
        assertThrows(ExecutionException.class, () -> rebeldeServiceImpl.findById(1L).get());
    }

    @Test
    public void shouldSaveRebelde(){
        final var expectedRebelde = RebeldeEntityTestFactory.aRebeldeEntity().withId(1L).build();
        when(mockedRebeldeRepository.save(expectedRebelde)).thenReturn(expectedRebelde);

        assertThat(rebeldeServiceImpl.save(expectedRebelde)).isEqualTo(expectedRebelde);
    }

    @Test
    public void whenUpdateLocalizacaoRebeldeShouldUseExistingLocalizacaoIfExists() throws ExecutionException, InterruptedException {
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
    public void whenUpdateLocalizacaoRebeldeShouldUseProvidedLocalizacaoIfNotExists() throws ExecutionException, InterruptedException {
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

    @Test
    public void shouldExecuteTransactionWhenItemsHasSameValue() throws ExecutionException, InterruptedException {
        var idRebelde1 = 1L;
        var idRebelde2 = 2L;
        Map<Long, Long> itemsRebelde1 = new HashMap<>();
        itemsRebelde1.put(1L, 1L);
        Map<Long, Long> itemsRebelde2 = new HashMap<>();
        itemsRebelde2.put(2L, 1L);

        final var itemInventario1 = getItemInventario(1L, 1L, idRebelde1, 1l);
        final var rebelde1 = itemInventario1.getRebelde();
        rebelde1.setInventario(Collections.singletonList(itemInventario1));
        when(mockedRebeldeRepository.findById(rebelde1.getId())).thenReturn(Optional.of(rebelde1));

        final var itemInventario2 = getItemInventario(2L, 2L, idRebelde2, 1l);
        final var rebelde2 = itemInventario2.getRebelde();
        rebelde2.setInventario(Collections.singletonList(itemInventario2));
        when(mockedRebeldeRepository.findById(rebelde2.getId())).thenReturn(Optional.of(rebelde2));

        rebeldeServiceImpl.executarTransacao(idRebelde1, idRebelde2, itemsRebelde1, itemsRebelde2);

        itemInventario1.setRebelde(rebelde2);
        verify(itemInventarioRepository).saveAll(Collections.singletonList(itemInventario1));
        itemInventario2.setRebelde(rebelde1);
        verify(itemInventarioRepository).saveAll(Collections.singletonList(itemInventario2));
    }

    @Disabled
    @Test
    public void shouldExecuteTransactionWhenRebeldeHasMoreThanOneItem() throws ExecutionException, InterruptedException {
        var idRebelde1 = 1L;
        var idRebelde2 = 2L;
        Map<Long, Long> itemsRebelde1 = new HashMap<>();
        itemsRebelde1.put(1L, 1L);
        Map<Long, Long> itemsRebelde2 = new HashMap<>();
        itemsRebelde2.put(2L, 1L);

        final var itemInventario1 = getItemInventario(1L, 1L, idRebelde1, 2l);
        final var rebelde1 = itemInventario1.getRebelde();
        rebelde1.setInventario(Collections.singletonList(itemInventario1));
        when(mockedRebeldeRepository.findById(rebelde1.getId())).thenReturn(Optional.of(rebelde1));

        final var itemInventario2 = getItemInventario(2L, 2L, idRebelde2, 1l);
        final var rebelde2 = itemInventario2.getRebelde();
        rebelde2.setInventario(Collections.singletonList(itemInventario2));
        when(mockedRebeldeRepository.findById(rebelde2.getId())).thenReturn(Optional.of(rebelde2));

        rebeldeServiceImpl.executarTransacao(idRebelde1, idRebelde2, itemsRebelde1, itemsRebelde2);

        var expectedItemInventario1 =  getItemInventario(1L, 1L, idRebelde1, 1l);
        expectedItemInventario1.getRebelde().setInventario(Arrays.asList(expectedItemInventario1));
        expectedItemInventario1.equals(itemInventario1);
        verify(itemInventarioRepository).saveAll(Arrays.asList(expectedItemInventario1, itemInventario2));

        itemInventario2.setRebelde(rebelde1);
        verify(itemInventarioRepository).saveAll(Arrays.asList(itemInventario2));
    }



    private ItemInventarioEntity getItemInventario(final Long idItemInventario,
                                                   final Long idItem,
                                                   final Long idRebelde,
                                                   final Long quantidade) {
        return new ItemInventarioEntity(idItemInventario,
                new ItemEntity(idItem, null, null, null),
                RebeldeEntityTestFactory.aRebeldeEntity().withId(idRebelde).build(),
                quantidade);
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