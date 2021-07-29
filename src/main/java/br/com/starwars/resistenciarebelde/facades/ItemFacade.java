package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.ItemDTO;

import java.util.List;

public interface ItemFacade {

    List<ItemDTO> findAll();

    ItemDTO save(ItemDTO itemDTO);

}
