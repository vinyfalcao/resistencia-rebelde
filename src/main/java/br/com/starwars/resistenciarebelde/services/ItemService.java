package br.com.starwars.resistenciarebelde.services;

import br.com.starwars.resistenciarebelde.entities.ItemEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ItemService {

    CompletableFuture<List<ItemEntity>> findAll();

    CompletableFuture<ItemEntity> save(ItemEntity itemEntity);

}
