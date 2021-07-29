package br.com.starwars.resistenciarebelde.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRebeldeDTO {

    private String nome;
    private Long idade;
    private String genero;
    private boolean traidor;
    private LocalizacaoRebeldeDTO localizacaoRebeldeDTO;

}
