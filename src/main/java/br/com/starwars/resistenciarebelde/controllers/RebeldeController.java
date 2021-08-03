package br.com.starwars.resistenciarebelde.controllers;

import br.com.starwars.resistenciarebelde.dtos.*;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/rebeldes")
@RequiredArgsConstructor
public class RebeldeController {

    private final RebeldeFacade rebeldeFacade;
    private final ExecutorService threadPool;

    @GetMapping
    public List<CreateRebeldeDTO> findAll(){
        return rebeldeFacade.findAll();
    }

    @GetMapping("/{id}")
    public CreateRebeldeDTO findById(@PathVariable final Long id){
        return rebeldeFacade.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateRebeldeDTO createNew(@RequestBody final CreateRebeldeDTO createRebeldeDTO){
        return rebeldeFacade.createNew(createRebeldeDTO);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/async")
    public CompletableFuture<Void> createNewAsync(@RequestBody final CreateRebeldeDTO createRebeldeDTO){
        return rebeldeFacade.createNewAsync(createRebeldeDTO)
                .thenRunAsync(() -> System.out.println("Executou controller"));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@RequestBody final UpdateRebeldeDTO updateRebeldeDTO){
        rebeldeFacade.updateRebelde(updateRebeldeDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    public void updateLocalizacao(@RequestBody final UpdateLocalizacaoRebeldeDTO updateLocalizacaoRebeldeDTO){
        rebeldeFacade.updateLocalizacao(updateLocalizacaoRebeldeDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reportarTraicao")
    public void reportarTraicao(@RequestBody final RegistroTraicaoDTO registroTraicaoDTO){
        rebeldeFacade.reportarTraicao(registroTraicaoDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/transacao")
    public void executarTransacao(@RequestBody final TransacaoItemsRebeldeDTO transacaoItemsRebeldeDTO){
        rebeldeFacade.executarTransacao(transacaoItemsRebeldeDTO);
    }




}