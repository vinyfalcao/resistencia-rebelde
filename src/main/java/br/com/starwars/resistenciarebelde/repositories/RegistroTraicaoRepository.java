package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.RegistroTraicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroTraicaoRepository extends JpaRepository<RegistroTraicaoEntity, Long> {

    List<RegistroTraicaoEntity> findByReportadoId(Long idReportado);

}
