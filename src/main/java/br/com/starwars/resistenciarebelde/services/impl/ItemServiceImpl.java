package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.repositories.ItemRepository;
import br.com.starwars.resistenciarebelde.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public CompletableFuture<List<ItemEntity>> findAll() {
        return itemRepository.findAllBy();
    }

    @Override
    public CompletableFuture<ItemEntity> save(ItemEntity itemEntity) {
        return CompletableFuture.supplyAsync(() -> itemRepository.save(itemEntity));
    }
}
