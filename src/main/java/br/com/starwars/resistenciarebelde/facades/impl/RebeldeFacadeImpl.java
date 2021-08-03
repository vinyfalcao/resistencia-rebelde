package br.com.starwars.resistenciarebelde.facades.impl;

import br.com.starwars.resistenciarebelde.dtos.*;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import br.com.starwars.resistenciarebelde.services.RegistroTraicaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    @Override
    public void executarTransacao(final TransacaoItemsRebeldeDTO transacao) {
        final Map<Long, Long> proposta1 = generateTransactionItemsMap(transacao.getProposta1());
        final Map<Long, Long> proposta2 = generateTransactionItemsMap(transacao.getProposta2());
        rebeldeService.executarTransacao(transacao.getIdRebelde1(), transacao.getIdRebelde2(), proposta1, proposta2);
    }

    @Async
    @Override
    public CompletableFuture<Void> createNewAsync(CreateRebeldeDTO createRebeldeDTO) {
        return CompletableFuture.runAsync(() -> {
            this.rebeldeService.save(toRebeldeEntity(createRebeldeDTO));
            System.out.println("Rebelde foi salvo na thread " + Thread.currentThread().getName());
        });
    }

    private Map<Long, Long> generateTransactionItemsMap(List<PropostaTransacaoDTO> proposta) {
        final Map<Long, Long> map = new HashMap<>();
        proposta.forEach(item -> map.put(item.getIdItem(), item.getQuantidade()));
        return map;
    }

    private CreateRebeldeDTO toRebeldeDTO(final RebeldeEntity rebeldeEntity){
        return modelMapper.map(rebeldeEntity, CreateRebeldeDTO.class);
    }

    private RebeldeEntity toRebeldeEntity(final CreateRebeldeDTO createRebeldeDTO){
        return modelMapper.map(createRebeldeDTO, RebeldeEntity.class);
    }

}
