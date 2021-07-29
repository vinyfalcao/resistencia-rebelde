package br.com.starwars.resistenciarebelde.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

//@Embeddable
@Data
public class RegistroTraicaoPK implements Serializable {

    private Long relator;
    private Long reportado;

}
