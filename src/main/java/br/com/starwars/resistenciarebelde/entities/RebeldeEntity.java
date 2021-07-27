package br.com.starwars.resistenciarebelde.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RebeldeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private Long idade;
    private String genero;

    @ManyToOne
    @JoinColumn(name = "localizacao_id")
    private LocalizacaoRebeldeEntity localizacao;

}
