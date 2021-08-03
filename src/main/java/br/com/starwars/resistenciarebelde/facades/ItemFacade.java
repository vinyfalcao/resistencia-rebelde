package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.ItemDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ItemFacade {

    CompletableFuture<List<ItemDTO>> findAll();

    CompletableFuture<ItemDTO> save(ItemDTO itemDTO);

}
