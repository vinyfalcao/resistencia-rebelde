package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRebeldeRepository extends JpaRepository<LocalizacaoRebeldeEntity, Long> {
}
