package br.com.starwars.resistenciarebelde.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tabela_traicao", uniqueConstraints = @UniqueConstraint(columnNames = {"relator_id", "reportado_id"}))
@NoArgsConstructor
@Data
public class RegistroTraicaoEntity {

    //@EmbeddedId
    //private RegistroTraicaoPK id;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relator_id")
    private RebeldeEntity relator;

    @ManyToOne
    @JoinColumn(name = "reportado_id")
    private RebeldeEntity reportado;

    public RegistroTraicaoEntity(RebeldeEntity relator, RebeldeEntity reportado) {
        this.relator = relator;
        this.reportado = reportado;
    }
}
