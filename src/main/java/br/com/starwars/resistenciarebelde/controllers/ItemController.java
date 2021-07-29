package br.com.starwars.resistenciarebelde.controllers;

import br.com.starwars.resistenciarebelde.dtos.ItemDTO;
import br.com.starwars.resistenciarebelde.facades.ItemFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemFacade itemFacade;

    @GetMapping
    public List<ItemDTO> findAll(){
        return itemFacade.findAll();
    }

    @PostMapping
    public ItemDTO save(@RequestBody final ItemDTO itemDTO){
        return itemFacade.save(itemDTO);
    }

}
