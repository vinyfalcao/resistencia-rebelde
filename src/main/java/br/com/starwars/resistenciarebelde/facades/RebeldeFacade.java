package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.RebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.RegistroTraicaoDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;

import java.util.List;

public interface RebeldeFacade {

    List<RebeldeDTO> findAll();

    RebeldeDTO findById(Long id);

    RebeldeDTO save(RebeldeDTO rebeldeDTO);

    void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao);

    void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO);

}
