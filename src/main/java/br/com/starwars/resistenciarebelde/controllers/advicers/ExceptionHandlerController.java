package br.com.starwars.resistenciarebelde.controllers.advicers;

import br.com.starwars.resistenciarebelde.exceptions.RebeldeNaoLocalizadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(RuntimeException ex, WebRequest request){
        return new ResponseEntity<>(
                new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RebeldeNaoLocalizadoException.class)
    public ResponseEntity<ResponseObject> handleRebeldeNaoLocalizadoException(RuntimeException ex, WebRequest request){
        return new ResponseEntity<>(
                new ResponseObject(HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

}
