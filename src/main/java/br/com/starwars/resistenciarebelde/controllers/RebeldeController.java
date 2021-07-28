package br.com.starwars.resistenciarebelde.controllers;

import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rebeldes")
@RequiredArgsConstructor
public class RebeldeController {

    private final RebeldeService rebeldeService;

    @GetMapping
    public List<RebeldeEntity> findAll(){
        return rebeldeService.findAll();
    }

    @GetMapping("/{id}")
    public RebeldeEntity findById(@PathVariable final Long id){
        return rebeldeService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RebeldeEntity createNew(@RequestBody final RebeldeEntity rebeldeEntity){
        return rebeldeService.save(rebeldeEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@RequestBody final RebeldeEntity rebeldeEntity){
        rebeldeService.save(rebeldeEntity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    public void updateLocalizacao(@RequestBody final UpdateLocalizacaoRebeldeDTO updateLocalizacaoRebeldeDTO){
        rebeldeService.updateLocalizacao(updateLocalizacaoRebeldeDTO);
    }

}