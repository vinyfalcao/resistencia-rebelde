package br.com.starwars.resistenciarebelde.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private boolean traidor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "localizacao_id")
    private LocalizacaoRebeldeEntity localizacao;

    @OneToMany(mappedBy = "relator")
    private List<RegistroTraicaoEntity> reportsRelatados;
    @OneToMany(mappedBy = "reportado")
    private List<RegistroTraicaoEntity> reportsRecebidos;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "rebelde", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<ItemInventarioEntity> inventario;

}
