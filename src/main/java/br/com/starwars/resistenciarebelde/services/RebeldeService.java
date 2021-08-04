package br.com.starwars.resistenciarebelde.services;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface RebeldeService {

    CompletableFuture<List<RebeldeEntity>> findAll();

    CompletableFuture<RebeldeEntity> findById(Long id);

    RebeldeEntity save(RebeldeEntity rebeldeEntity);

    CompletableFuture<Void> updateLocalizacao(Long idRebelde, LocalizacaoRebeldeEntity localizacao) throws ExecutionException, InterruptedException;

    void executarTransacao(Long idRebelde1, Long idRebelde2, Map<Long, Long> itemsRebelde1, Map<Long, Long> itemsRebelde2) throws ExecutionException, InterruptedException;


}
