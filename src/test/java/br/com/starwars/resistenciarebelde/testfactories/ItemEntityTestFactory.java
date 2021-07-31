package br.com.starwars.resistenciarebelde.testfactories;

import br.com.starwars.resistenciarebelde.entities.ItemEntity;

public class ItemEntityTestFactory {

    private ItemEntityTestFactory(){}

    public static ItemEntity generateItemInstance(){
        return new ItemEntity(null, "Água", 1L);
    }

}
