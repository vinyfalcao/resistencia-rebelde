package br.com.starwars.resistenciarebelde.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebeldeDTO {

    private Long id;
    private String nome;
    private Long idade;
    private String genero;

}
