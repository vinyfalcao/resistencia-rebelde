package br.com.starwars.resistenciarebelde.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRebeldeDTO {

    private String nome;
    private Long idade;
    private String genero;
    private LocalizacaoRebeldeDTO localizacaoRebeldeDTO;
    private List<ItemInventarioRebeldeDTO> itemInventarioRebeldeDTO;

}
