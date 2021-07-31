package br.com.starwars.resistenciarebelde.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TransacaoItemsRebeldeDTO {

    private Long idRebelde1;
    private List<PropostaTransacaoDTO> proposta1;
    private Long idRebelde2;
    private List<PropostaTransacaoDTO> proposta2;

}
