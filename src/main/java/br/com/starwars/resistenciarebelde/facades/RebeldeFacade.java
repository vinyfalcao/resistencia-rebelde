package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.RegistroTraicaoDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateRebeldeDTO;

import java.util.List;

public interface RebeldeFacade {

    List<CreateRebeldeDTO> findAll();

    CreateRebeldeDTO findById(Long id);

    CreateRebeldeDTO createNew(CreateRebeldeDTO createRebeldeDTO);

    UpdateRebeldeDTO updateRebelde(UpdateRebeldeDTO updateRebeldeDTO);

    void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao);

    void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO);

}
