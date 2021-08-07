package br.com.starwars.resistenciarebelde.consumers;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class JmsErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
