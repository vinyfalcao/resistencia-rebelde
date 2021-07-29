package br.com.starwars.resistenciarebelde.facades.impl;

import br.com.starwars.resistenciarebelde.dtos.LocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.RebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.RegistroTraicaoDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import br.com.starwars.resistenciarebelde.services.RegistroTraicaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class RebeldeFacadeImpl implements RebeldeFacade {

    private final RebeldeService rebeldeService;
    private final RegistroTraicaoService registroTraicaoService;
    private final ModelMapper modelMapper;

    @Override
    public List<RebeldeDTO> findAll() {
        return this.rebeldeService.findAll().stream()
                .map(this::toRebeldeDTO)
                .collect(toList());
    }

    @Override
    public RebeldeDTO findById(final Long id) {
        return toRebeldeDTO(this.rebeldeService.findById(id));
    }

    @Override
    public RebeldeDTO save(final RebeldeDTO rebeldeDTO) {
        return toRebeldeDTO(this.rebeldeService.save(toRebeldeEntity(rebeldeDTO)));
    }

    @Override
    public void updateLocalizacao(UpdateLocalizacaoRebeldeDTO localizacao) {
        this.rebeldeService.updateLocalizacao(localizacao.getIdRebelde(),
                modelMapper.map(localizacao.getLocalizacaoRebeldeDto(), LocalizacaoRebeldeEntity.class));
    }

    @Override
    public void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO) {
        registroTraicaoService.reportarTraicao(registroTraicaoDTO.getIdRelator(), registroTraicaoDTO.getIdReportado());
    }

    private RebeldeDTO toRebeldeDTO(final RebeldeEntity rebeldeEntity){
        var rebeldeDTO = modelMapper.map(rebeldeEntity, RebeldeDTO.class);
        if(rebeldeEntity.getLocalizacao() != null){
            rebeldeDTO.setLocalizacaoRebeldeDTO(modelMapper.map(rebeldeEntity.getLocalizacao(),
                    LocalizacaoRebeldeDTO.class));
        }
        return rebeldeDTO;
    }

    private RebeldeEntity toRebeldeEntity(final RebeldeDTO rebeldeDTO){
        var rebeldeEntity = modelMapper.map(rebeldeDTO, RebeldeEntity.class);
        if(rebeldeDTO.getLocalizacaoRebeldeDTO() != null){
            rebeldeEntity.setLocalizacao(
                    modelMapper.map(rebeldeDTO.getLocalizacaoRebeldeDTO(), LocalizacaoRebeldeEntity.class));
        }
        return rebeldeEntity;
    }

}
