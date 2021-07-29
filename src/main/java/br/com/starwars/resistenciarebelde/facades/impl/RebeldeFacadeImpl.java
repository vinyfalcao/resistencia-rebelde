package br.com.starwars.resistenciarebelde.facades.impl;

import br.com.starwars.resistenciarebelde.dtos.*;
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
    public List<CreateRebeldeDTO> findAll() {
        return this.rebeldeService.findAll().stream()
                .map(this::toRebeldeDTO)
                .collect(toList());
    }

    @Override
    public CreateRebeldeDTO findById(final Long id) {
        return toRebeldeDTO(this.rebeldeService.findById(id));
    }

    @Override
    public CreateRebeldeDTO createNew(final CreateRebeldeDTO createRebeldeDTO) {
        return toRebeldeDTO(this.rebeldeService.save(toRebeldeEntity(createRebeldeDTO)));
    }

    @Override
    public UpdateRebeldeDTO updateRebelde(UpdateRebeldeDTO updateRebeldeDTO) {
        final RebeldeEntity entity = this.rebeldeService.save(
                modelMapper.map(updateRebeldeDTO, RebeldeEntity.class));
        return modelMapper.map(entity, UpdateRebeldeDTO.class);
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

    private CreateRebeldeDTO toRebeldeDTO(final RebeldeEntity rebeldeEntity){
        var rebeldeDTO = modelMapper.map(rebeldeEntity, CreateRebeldeDTO.class);
        if(rebeldeEntity.getLocalizacao() != null){
            rebeldeDTO.setLocalizacaoRebeldeDTO(modelMapper.map(rebeldeEntity.getLocalizacao(),
                    LocalizacaoRebeldeDTO.class));
        }
        return rebeldeDTO;
    }

    private RebeldeEntity toRebeldeEntity(final CreateRebeldeDTO createRebeldeDTO){
        var rebeldeEntity = modelMapper.map(createRebeldeDTO, RebeldeEntity.class);
        if(createRebeldeDTO.getLocalizacaoRebeldeDTO() != null){
            rebeldeEntity.setLocalizacao(
                    modelMapper.map(createRebeldeDTO.getLocalizacaoRebeldeDTO(), LocalizacaoRebeldeEntity.class));
        }
        return rebeldeEntity;
    }

}
