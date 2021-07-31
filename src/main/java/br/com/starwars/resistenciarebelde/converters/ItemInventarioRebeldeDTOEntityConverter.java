package br.com.starwars.resistenciarebelde.converters;

import br.com.starwars.resistenciarebelde.dtos.ItemInventarioRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemInventarioRebeldeDTOEntityConverter extends AbstractConverter<ItemInventarioRebeldeDTO, ItemInventarioEntity> {
    @Override
    protected ItemInventarioEntity convert(ItemInventarioRebeldeDTO source) {
        var entity = new ModelMapper().map(source, ItemInventarioEntity.class);
        entity.setId(null);
        entity.setItem(new ItemEntity(source.getItemId(), null, null));
        entity.setRebelde(null);
        return entity;
    }
}
