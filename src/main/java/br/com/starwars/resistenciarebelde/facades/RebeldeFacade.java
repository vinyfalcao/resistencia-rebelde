package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.*;

import java.util.List;

public interface RebeldeFacade {

    List<CreateRebeldeDTO> findAll();

    CreateRebeldeDTO findById(Long id);

    CreateRebeldeDTO createNew(CreateRebeldeDTO createRebeldeDTO);

    UpdateRebeldeDTO updateRebelde(UpdateRebeldeDTO updateRebeldeDTO);

    void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao);

    void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO);

    void executarTransacao(TransacaoItemsRebeldeDTO transacao);



}
