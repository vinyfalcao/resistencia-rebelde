package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.RegistroTraicaoEntity;
import br.com.starwars.resistenciarebelde.repositories.RegistroTraicaoRepository;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import br.com.starwars.resistenciarebelde.testfactories.RebeldeEntityTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistroTraicaoServiceImplTest {


    @Mock
    private RebeldeService rebeldeService;
    @Mock
    private RegistroTraicaoRepository registroTraicaoRepository;
    @InjectMocks
    private RegistroTraicaoServiceImpl registroTraicaoServiceImpl;

    @Test
    public void shouldSaveRegistroTraicao() throws ExecutionException, InterruptedException {
        final long ID_RELATOR = 1L;
        final long ID_REPORTADO = 2L;
        final var rebeldeRelator = RebeldeEntityTestFactory.aRebeldeEntity()
                .withId(ID_RELATOR)
                .build();
        when(rebeldeService.findById(rebeldeRelator.getId())).thenReturn(CompletableFuture.completedFuture(rebeldeRelator));
        final var rebeldeReportado = RebeldeEntityTestFactory.aRebeldeEntity()
                .withId(ID_REPORTADO)
                .build();
        when(rebeldeService.findById(rebeldeReportado.getId())).thenReturn(CompletableFuture.completedFuture(rebeldeReportado));

        registroTraicaoServiceImpl.reportarTraicao(ID_RELATOR, ID_REPORTADO);

        verify(registroTraicaoRepository).save(new RegistroTraicaoEntity(rebeldeRelator, rebeldeReportado));
    }

}