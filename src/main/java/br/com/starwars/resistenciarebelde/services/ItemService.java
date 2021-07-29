package br.com.starwars.resistenciarebelde.services;

import br.com.starwars.resistenciarebelde.entities.ItemEntity;

import java.util.List;

public interface ItemService {

    List<ItemEntity> findAll();

    ItemEntity save(ItemEntity itemEntity);

}
