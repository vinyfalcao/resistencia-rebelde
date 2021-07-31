package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemInventarioRepository extends JpaRepository<ItemInventarioEntity, Long> {
}
