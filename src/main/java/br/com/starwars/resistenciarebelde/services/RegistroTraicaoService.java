package br.com.starwars.resistenciarebelde.services;

import java.util.concurrent.ExecutionException;

public interface RegistroTraicaoService {

    void reportarTraicao(Long idRelator, Long idReportado) throws ExecutionException, InterruptedException;

}
