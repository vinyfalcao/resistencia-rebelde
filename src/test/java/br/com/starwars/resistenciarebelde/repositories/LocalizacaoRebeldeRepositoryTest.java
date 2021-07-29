package br.com.starwars.resistenciarebelde.repositories;

import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocalizacaoRebeldeRepositoryTest {

    @Autowired
    private LocalizacaoRebeldeRepository localizacaoRebeldeRepository;

    @BeforeEach
    void setUp(){
        localizacaoRebeldeRepository.deleteAll();
    }

    @Test
    public void shouldNotBeAbleToInsertDuplicatedLocalizacao(){
        final var localizacao1 = generateLocalizacao();
        final var localizacao2 = generateLocalizacao();
        localizacaoRebeldeRepository.save(localizacao1);
        assertThrows(DataIntegrityViolationException.class, () -> localizacaoRebeldeRepository.save(localizacao2));
    }

    private LocalizacaoRebeldeEntity generateLocalizacao() {
        return new LocalizacaoRebeldeEntity(null, "Galaxia Teste", 10.0, 10.0, null);
    }

}