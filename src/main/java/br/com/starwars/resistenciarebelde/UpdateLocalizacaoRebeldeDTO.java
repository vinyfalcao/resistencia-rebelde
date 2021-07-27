package br.com.starwars.resistenciarebelde;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import lombok.Data;

@Data
public class UpdateLocalizacaoRebeldeDTO {

    private Long idRebelde;
    private LocalizacaoRebeldeEntity localizacaoRebeldeEntity;

}
