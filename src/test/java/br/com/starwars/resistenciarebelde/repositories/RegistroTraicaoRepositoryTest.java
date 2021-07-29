package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RegistroTraicaoEntity;
import br.com.starwars.resistenciarebelde.entities.RegistroTraicaoPK;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistroTraicaoRepositoryTest {

    @Autowired
    private RegistroTraicaoRepository registroTraicaoRepository;
    @Autowired
    private RebeldeRepository rebeldeRepository;


    @Test
    public void shouldNotInsertDuplicatedRegisters(){
        final var rebelde1 = rebeldeRepository.save(generateRebeldeInstance("Nome 1"));
        final var rebelde2 = rebeldeRepository.save(generateRebeldeInstance("Nome 2"));

        registroTraicaoRepository.save(new RegistroTraicaoEntity(rebelde1, rebelde2));
        assertThrows(DataIntegrityViolationException.class,
                () -> registroTraicaoRepository.save(new RegistroTraicaoEntity(rebelde1, rebelde2)));
    }

    private RebeldeEntity generateRebeldeInstance(final String nome){
        return new RebeldeEntity(null, nome, 500L, "Genero",false,  null, null, null);
    }




}