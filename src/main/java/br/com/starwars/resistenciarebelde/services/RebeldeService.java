package br.com.starwars.resistenciarebelde.services;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;

import java.util.List;

public interface RebeldeService {

    List<RebeldeEntity> findAll();

    RebeldeEntity findById(Long id);

    RebeldeEntity save(RebeldeEntity rebeldeEntity);

    void updateLocalizacao(Long idRebelde, LocalizacaoRebeldeEntity localizacao);


}
