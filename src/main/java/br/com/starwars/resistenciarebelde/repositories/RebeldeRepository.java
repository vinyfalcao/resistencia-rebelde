package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebeldeRepository extends JpaRepository<RebeldeEntity, Long> {
}
