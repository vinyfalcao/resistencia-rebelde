package br.com.starwars.resistenciarebelde.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalizacaoRebeldeDTO {

    private Long id;
    private String nomeGalaxia;
    private Double latitude;
    private Double longitude;

}
