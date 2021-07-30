package br.com.starwars.resistenciarebelde.converters;

import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.LocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RebeldeEntityCreateRebeldeDTOConverter extends AbstractConverter<RebeldeEntity, CreateRebeldeDTO> {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    protected CreateRebeldeDTO convert(RebeldeEntity rebeldeEntity) {
        final var rebeldeDTO = modelMapper.map(rebeldeEntity, CreateRebeldeDTO.class);
        if(rebeldeEntity.getLocalizacao() != null){
            rebeldeDTO.setLocalizacaoRebeldeDTO(modelMapper.map(rebeldeEntity.getLocalizacao(),
                    LocalizacaoRebeldeDTO.class));
        }
        return rebeldeDTO;
    }
}
