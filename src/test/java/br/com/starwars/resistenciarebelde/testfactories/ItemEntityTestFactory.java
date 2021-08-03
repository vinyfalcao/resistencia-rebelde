package br.com.starwars.resistenciarebelde.testfactories;

import br.com.starwars.resistenciarebelde.entities.ItemEntity;

public class ItemEntityTestFactory {

    private ItemEntityTestFactory(){}

    public static ItemEntity.ItemEntityBuilder generateItemInstance(){
        return ItemEntity.builder()
                .withNome("Agua")
                .withPontos(1L);
    }


}
