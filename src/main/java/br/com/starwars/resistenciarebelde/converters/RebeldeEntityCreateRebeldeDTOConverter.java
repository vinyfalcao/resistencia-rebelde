package br.com.starwars.resistenciarebelde.converters;

import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.ItemInventarioRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.LocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

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
        if(rebeldeEntity.getInventario() != null){
            var inventario = rebeldeEntity.getInventario().stream()
                    .map(entity -> modelMapper.map(entity, ItemInventarioRebeldeDTO.class))
                    .collect(toList());
            rebeldeDTO.setItemInventarioRebeldeDTO(inventario);
        }
        return rebeldeDTO;
    }
}
