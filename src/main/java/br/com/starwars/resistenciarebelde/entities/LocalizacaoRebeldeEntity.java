package br.com.starwars.resistenciarebelde.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nomeGalaxia", "latitude", "longitude"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalizacaoRebeldeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String nomeGalaxia;
    private Double latitude;
    private Double longitude;

    @OneToMany
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<RebeldeEntity> rebeldes;

}
