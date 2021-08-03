package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RebeldeRepository extends JpaRepository<RebeldeEntity, Long> {

    CompletableFuture<List<RebeldeEntity>> findAllBy();

}
