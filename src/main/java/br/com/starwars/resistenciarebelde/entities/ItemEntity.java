package br.com.starwars.resistenciarebelde.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder(setterPrefix = "with")
@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String nome;
    private Long pontos;

}
