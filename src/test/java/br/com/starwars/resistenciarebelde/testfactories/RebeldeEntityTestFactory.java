package br.com.starwars.resistenciarebelde.testfactories;

import br.com.starwars.resistenciarebelde.entities.builders.RebeldeEntityBuilder;

public class RebeldeEntityTestFactory {

    private RebeldeEntityTestFactory(){}

    public static RebeldeEntityBuilder aRebeldeEntity(){
        return new RebeldeEntityBuilder()
                .withId(1L)
                .withGenero("Genero")
                .withIdade(500L);
    }

}
