package br.com.starwars.resistenciarebelde.facades.impl;

import br.com.starwars.resistenciarebelde.dtos.ItemDTO;
import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.facades.ItemFacade;
import br.com.starwars.resistenciarebelde.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class ItemFacadeImpl implements ItemFacade {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @Override
    public List<ItemDTO> findAll() {
        return itemService.findAll().stream()
                .map(entity -> modelMapper.map(entity, ItemDTO.class))
                .collect(toList());
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        final ItemEntity itemEntity = itemService.save(modelMapper.map(itemDTO, ItemEntity.class));
        return modelMapper.map(itemEntity, ItemDTO.class);
    }
}
