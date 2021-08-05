package br.com.starwars.resistenciarebelde.controllers.advicers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseObject {

    private final Integer statusCode;
    private final String message;

}
