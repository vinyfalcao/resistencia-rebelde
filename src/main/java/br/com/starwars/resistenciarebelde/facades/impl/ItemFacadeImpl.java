package br.com.starwars.resistenciarebelde.facades.impl;

import br.com.starwars.resistenciarebelde.dtos.ItemDTO;
import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.facades.ItemFacade;
import br.com.starwars.resistenciarebelde.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class ItemFacadeImpl implements ItemFacade {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    //@Async
    @Override
    public CompletableFuture<List<ItemDTO>> findAll() {
        return itemService.findAll().thenApply(entities -> entities.stream()
                .map(entity -> modelMapper.map(entity, ItemDTO.class))
                .collect(toList()));
    }

    @Override
    public CompletableFuture<ItemDTO> save(ItemDTO itemDTO) {
        return itemService.save(modelMapper.map(itemDTO, ItemEntity.class))
                .thenApplyAsync(itemEntity -> modelMapper.map(itemEntity, ItemDTO.class));
    }
}
