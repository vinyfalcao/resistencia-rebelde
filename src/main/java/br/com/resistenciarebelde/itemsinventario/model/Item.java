package br.com.resistenciarebelde.itemsinventario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private UUID uuid;
    private String nome;
    private Long pontos;

}
