package br.com.starwars.resistenciarebelde.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item_inventario_rebelde",
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "rebelde_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInventarioEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rebelde_id")
    private RebeldeEntity rebelde;
    private Long quantidade;

}
