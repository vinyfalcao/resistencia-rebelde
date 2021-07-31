package br.com.starwars.resistenciarebelde.services;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;

import java.util.List;
import java.util.Map;

public interface RebeldeService {

    List<RebeldeEntity> findAll();

    RebeldeEntity findById(Long id);

    RebeldeEntity save(RebeldeEntity rebeldeEntity);

    void updateLocalizacao(Long idRebelde, LocalizacaoRebeldeEntity localizacao);

    void executarTransacao(Long idRebelde1, Long idRebelde2, Map<Long, Long> itemsRebelde1, Map<Long, Long> itemsRebelde2);


}
