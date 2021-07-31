package br.com.starwars.resistenciarebelde.converters;

import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateRebeldeDTOEntityConverter extends AbstractConverter<CreateRebeldeDTO, RebeldeEntity> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    protected RebeldeEntity convert(CreateRebeldeDTO createRebeldeDTO) {
        modelMapper.addConverter(new ItemInventarioRebeldeDTOEntityConverter());
        final var result = modelMapper.map(createRebeldeDTO, RebeldeEntity.class);
        if(createRebeldeDTO.getLocalizacaoRebeldeDTO() != null){
            result.setLocalizacao(
                    modelMapper.map(createRebeldeDTO.getLocalizacaoRebeldeDTO(), LocalizacaoRebeldeEntity.class));
        }
        if(createRebeldeDTO.getItemInventarioRebeldeDTO() != null){
            final List<ItemInventarioEntity> inventario = createRebeldeDTO.getItemInventarioRebeldeDTO().stream()
                    .map(dto -> modelMapper.map(dto, ItemInventarioEntity.class))
                    .collect(Collectors.toList());
            result.setInventario(inventario);
        }
        return result;
    }
}
