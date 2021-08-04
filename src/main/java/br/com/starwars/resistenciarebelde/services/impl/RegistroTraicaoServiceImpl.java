package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.entities.RegistroTraicaoEntity;
import br.com.starwars.resistenciarebelde.repositories.RegistroTraicaoRepository;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import br.com.starwars.resistenciarebelde.services.RegistroTraicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class RegistroTraicaoServiceImpl implements RegistroTraicaoService {

    private final RebeldeService rebeldeService;
    private final RegistroTraicaoRepository registroTraicaoRepository;

    @Override
    public void reportarTraicao(final Long idRelator, final Long idReportado) throws ExecutionException, InterruptedException {
        final var relator = rebeldeService.findById(idRelator).get();
        final var reportado = rebeldeService.findById(idReportado).get();
        registroTraicaoRepository.save(new RegistroTraicaoEntity(relator, reportado));
        final var qttReports = registroTraicaoRepository.findByReportadoId(reportado.getId()).size();
        if(qttReports >= 3 && !reportado.isTraidor()){
            reportado.setTraidor(true);
            rebeldeService.save(reportado);
        }

    }

}
