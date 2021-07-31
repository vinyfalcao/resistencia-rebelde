package br.com.starwars.resistenciarebelde.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropostaTransacaoDTO {

    private Long idItem;
    private Long quantidade;

}
