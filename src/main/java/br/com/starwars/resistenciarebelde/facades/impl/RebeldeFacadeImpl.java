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
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class RebeldeFacadeImpl implements RebeldeFacade {

    private final RebeldeService rebeldeService;
    private final RegistroTraicaoService registroTraicaoService;

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
                toLocalizacaoRebeldeEntity(localizacao.getLocalizacaoRebeldeDto()));
    }

    @Override
    public void reportarTraicao(RegistroTraicaoDTO registroTraicaoDTO) {
        registroTraicaoService.reportarTraicao(registroTraicaoDTO.getIdRelator(), registroTraicaoDTO.getIdReportado());
    }

    private RebeldeDTO toRebeldeDTO(final RebeldeEntity rebeldeEntity){
        return new RebeldeDTO(rebeldeEntity.getId(),
                rebeldeEntity.getNome(),
                rebeldeEntity.getIdade(),
                rebeldeEntity.getGenero(),
                rebeldeEntity.isTraidor(),
                rebeldeEntity.getLocalizacao() == null ? null : toLocalizacaoRebeldeDto(rebeldeEntity.getLocalizacao())
        );
    }

    private RebeldeEntity toRebeldeEntity(final RebeldeDTO rebeldeDTO){
        return new RebeldeEntity(rebeldeDTO.getId(),
                rebeldeDTO.getNome(),
                rebeldeDTO.getIdade(),
                rebeldeDTO.getGenero(),
                rebeldeDTO.isTraidor(),
                rebeldeDTO.getLocalizacaoRebeldeDTO() == null
                        ? null
                        : toLocalizacaoRebeldeEntity(rebeldeDTO.getLocalizacaoRebeldeDTO()),
                null,
                null
        );
    }

    private LocalizacaoRebeldeEntity toLocalizacaoRebeldeEntity(final LocalizacaoRebeldeDTO localizacaoRebeldeDTO){
        return new LocalizacaoRebeldeEntity(localizacaoRebeldeDTO.getId(),
                localizacaoRebeldeDTO.getNomeGalaxia(),
                localizacaoRebeldeDTO.getLatitude(),
                localizacaoRebeldeDTO.getLongitude(),
                null);
    }

    private LocalizacaoRebeldeDTO toLocalizacaoRebeldeDto(final LocalizacaoRebeldeEntity entity){
        return new LocalizacaoRebeldeDTO(entity.getId(),
                entity.getNomeGalaxia(),
                entity.getLatitude(),
                entity.getLongitude());
    }

}
