package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.repositories.ItemInventarioRepository;
import br.com.starwars.resistenciarebelde.repositories.LocalizacaoRebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RebeldeServiceImpl implements RebeldeService {

    private final RebeldeRepository rebeldeRepository;
    private final LocalizacaoRebeldeRepository localizacaoRebeldeRepository;
    private final ItemInventarioRepository itemInventarioRepository;

    @Override
    public CompletableFuture<List<RebeldeEntity>> findAll() {
        return rebeldeRepository.findAllBy();
    }

    @Override
    public CompletableFuture<RebeldeEntity> findById(final Long id) {
        return CompletableFuture.supplyAsync(() -> rebeldeRepository.findById(id))
                .thenApplyAsync(optionalRebelde -> optionalRebelde
                        .orElseThrow(() -> new RuntimeException("Rebelde não localizado")));
    }

    @Override
    public RebeldeEntity save(final RebeldeEntity rebeldeEntity) {
        if(rebeldeEntity.getInventario() != null){
            rebeldeEntity.getInventario().forEach(itemInventario -> itemInventario.setRebelde(rebeldeEntity));
        }
        return rebeldeRepository.save(rebeldeEntity);
    }

    @Override
    public void updateLocalizacao(final Long idRebelde, final LocalizacaoRebeldeEntity localizacao) throws ExecutionException, InterruptedException {
        //final var rebelde = rebeldeRepository.findById(idRebelde).orElseThrow(() -> new RuntimeException("Rebelde não localizado"));
        final var rebelde = rebeldeRepository.findById(idRebelde).get();
        final var localizacaoOptional = localizacaoRebeldeRepository.findByNomeGalaxiaAndLatitudeAndLongitude(localizacao.getNomeGalaxia(),
                localizacao.getLatitude(),
                localizacao.getLongitude());
        rebelde.setLocalizacao(localizacaoOptional.orElse(localizacao));
        save(rebelde);
    }

    @Override
    public void executarTransacao(Long idRebelde1,
                                  Long idRebelde2,
                                  Map<Long, Long> itemsRebelde1,
                                  Map<Long, Long> itemsRebelde2) throws ExecutionException, InterruptedException {
        var rebelde1 = findById(idRebelde1).get();
        var rebelde2 = findById(idRebelde2).get();
        var itemsInventario1 = rebelde1.getInventario().stream()
                .filter(itemInventario -> itemsRebelde1.containsKey(itemInventario.getItem().getId()))
                .collect(Collectors.toList());
        var itemsInventario2 = rebelde2.getInventario().stream()
                .filter(itemInventario -> itemsRebelde2.containsKey(itemInventario.getItem().getId()))
                .collect(Collectors.toList());

        final List<ItemInventarioEntity> itemsInventarioResult1
                = resolveItemsInventarioTransaction(itemsRebelde1, rebelde2, itemsInventario1, itemsInventario2);

        final List<ItemInventarioEntity> itemsInventarioResult2
                = resolveItemsInventarioTransaction(itemsRebelde2, rebelde1, itemsInventario2, itemsInventario1);

        itemInventarioRepository.saveAll(itemsInventarioResult1);
        itemInventarioRepository.saveAll(itemsInventarioResult2);
    }

    private List<ItemInventarioEntity> resolveItemsInventarioTransaction(Map<Long, Long> itemsRebelde,
                                                   RebeldeEntity destinationRebelde,
                                                   List<ItemInventarioEntity> itemsInventarioSource,
                                                   List<ItemInventarioEntity> itemsInventarioDestination ) {
        List<ItemInventarioEntity> result = new ArrayList<>();
        itemsInventarioSource.forEach(itemInventario -> {
            final Long transactionQuantity = itemsRebelde.get(itemInventario.getItem().getId());
            if(itemInventario.getQuantidade() > transactionQuantity){
                itemInventario.setQuantidade(itemInventario.getQuantidade() - transactionQuantity);
                result.add(
                        new ItemInventarioEntity(
                                itemInventario.getId(),
                                itemInventario.getItem(),
                                destinationRebelde,
                                transactionQuantity));

            }else{
                itemInventario.setRebelde(destinationRebelde);
                result.add(itemInventario);
            }
        });
        return result;
    }

}