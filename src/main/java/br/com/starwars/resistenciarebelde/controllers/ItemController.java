package br.com.starwars.resistenciarebelde.controllers;

import br.com.starwars.resistenciarebelde.dtos.ItemDTO;
import br.com.starwars.resistenciarebelde.facades.ItemFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemFacade itemFacade;

    @GetMapping
    @Async
    public CompletableFuture<List<ItemDTO>> findAll(){
        return itemFacade.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Async
    public CompletableFuture<ItemDTO> save(@RequestBody final ItemDTO itemDTO){
        return itemFacade.save(itemDTO);
    }

}
