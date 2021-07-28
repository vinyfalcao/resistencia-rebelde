package br.com.starwars.resistenciarebelde.facades.impl;

import br.com.starwars.resistenciarebelde.dtos.RebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class RebeldeFacadeImpl implements RebeldeFacade {

    private final RebeldeService rebeldeService;

    @Override
    public List<RebeldeDTO> findAll() {
        return this.rebeldeService.findAll().stream()
                .map(this::toDTO)
                .collect(toList());
    }

    @Override
    public RebeldeDTO findById(Long id) {
        return null;
    }

    @Override
    public RebeldeDTO save(RebeldeDTO rebeldeDTO) {
        return null;
    }

    @Override
    public void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao) {

    }

    private RebeldeDTO toDTO(final RebeldeEntity rebeldeEntity){
        return new RebeldeDTO(rebeldeEntity.getId(),
                rebeldeEntity.getNome(),
                rebeldeEntity.getIdade(),
                rebeldeEntity.getGenero()
        );
    }
}
