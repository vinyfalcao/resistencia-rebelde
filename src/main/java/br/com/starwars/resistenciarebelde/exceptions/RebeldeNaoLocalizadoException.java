package br.com.starwars.resistenciarebelde.exceptions;

public class RebeldeNaoLocalizadoException extends RuntimeException{

    public RebeldeNaoLocalizadoException(final Long idRebelde) {
        super("Rebelde com o id " + idRebelde + " não localizado");
    }
}
