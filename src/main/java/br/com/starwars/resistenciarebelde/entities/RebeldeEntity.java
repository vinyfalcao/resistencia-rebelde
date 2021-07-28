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
    @Column(unique = true)
    private String nome;
    private Long idade;
    private String genero;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "localizacao_id")
    private LocalizacaoRebeldeEntity localizacao;

}
