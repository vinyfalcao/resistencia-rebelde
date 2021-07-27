package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalizacaoRebeldeRepository extends JpaRepository<LocalizacaoRebeldeEntity, Long> {

    Optional<LocalizacaoRebeldeEntity> findByNomeGalaxiaAndLatitudeAndLongitude(String nomeGalaxia, Double latitude, Double longitude);

}
