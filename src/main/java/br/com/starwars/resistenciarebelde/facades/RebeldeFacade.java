package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface RebeldeFacade {

    CompletableFuture<List<CreateRebeldeDTO>> findAll();

    CompletableFuture<CreateRebeldeDTO> findById(Long id);

    CreateRebeldeDTO createNew(CreateRebeldeDTO createRebeldeDTO);

    UpdateRebeldeDTO updateRebelde(UpdateRebeldeDTO updateRebeldeDTO);

    void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao) throws ExecutionException, InterruptedException;

    void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO) throws ExecutionException, InterruptedException;

    void executarTransacao(TransacaoItemsRebeldeDTO transacao) throws ExecutionException, InterruptedException;


    CompletableFuture<Void> createNewAsync(CreateRebeldeDTO createRebeldeDTO);
}
