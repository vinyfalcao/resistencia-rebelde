package br.com.starwars.resistenciarebelde.entities.builders;

import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RegistroTraicaoEntity;

import java.util.List;

public class RebeldeEntityBuilder {

    private Long id;
    private String nome;
    private Long idade;
    private String genero;
    private boolean traidor;
    private LocalizacaoRebeldeEntity localizacao;
    private List<RegistroTraicaoEntity> reportsRelatados;
    private List<RegistroTraicaoEntity> reportsRecebidos;
    private List<ItemInventarioEntity> inventario;

    public RebeldeEntityBuilder withId(final Long id){
        this.id = id;
        return this;
    }

    public RebeldeEntityBuilder withNome(final String nome){
        this.nome = nome;
        return this;
    }

    public RebeldeEntityBuilder withIdade(final Long idade){
        this.idade = idade;
        return this;
    }

    public RebeldeEntityBuilder withGenero(final String genero){
        this.genero = genero;
        return this;
    }

    public RebeldeEntityBuilder isTraidor(final boolean traidor){
        this.traidor = traidor;
        return this;
    }

    public RebeldeEntityBuilder withLocalizacao(final LocalizacaoRebeldeEntity localizacao){
        this.localizacao = localizacao;
        return this;
    }

    public RebeldeEntityBuilder withReportsRelatados(final List<RegistroTraicaoEntity> reportsRelatados){
        this.reportsRelatados = reportsRelatados;
        return this;
    }

    public RebeldeEntityBuilder withReportsRecebidos(final List<RegistroTraicaoEntity> reportsRecebidos){
        this.reportsRecebidos = reportsRecebidos;
        return this;
    }

    public RebeldeEntityBuilder withInventario(List<ItemInventarioEntity> inventario) {
        this.inventario = inventario;
        return this;
    }

    public RebeldeEntity build(){
        return new RebeldeEntity(id, nome, idade, genero, traidor, localizacao, reportsRelatados, reportsRecebidos, inventario);
    }


}
