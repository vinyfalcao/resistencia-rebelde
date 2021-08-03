package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RebeldeFacade {

    CompletableFuture<List<CreateRebeldeDTO>> findAll();

    CreateRebeldeDTO findById(Long id);

    CreateRebeldeDTO createNew(CreateRebeldeDTO createRebeldeDTO);

    UpdateRebeldeDTO updateRebelde(UpdateRebeldeDTO updateRebeldeDTO);

    void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao);

    void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO);

    void executarTransacao(TransacaoItemsRebeldeDTO transacao);


    CompletableFuture<Void> createNewAsync(CreateRebeldeDTO createRebeldeDTO);
}
