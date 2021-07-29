package br.com.starwars.resistenciarebelde.controllers;

import br.com.starwars.resistenciarebelde.dtos.RebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.RegistroTraicaoDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rebeldes")
@RequiredArgsConstructor
public class RebeldeController {

    private final RebeldeFacade rebeldeFacade;

    @GetMapping
    public List<RebeldeDTO> findAll(){
        return rebeldeFacade.findAll();
    }

    @GetMapping("/{id}")
    public RebeldeDTO findById(@PathVariable final Long id){
        return rebeldeFacade.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RebeldeDTO createNew(@RequestBody final RebeldeDTO rebeldeDTO){
        return rebeldeFacade.save(rebeldeDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void update(@RequestBody final RebeldeDTO rebeldeDTO){
        rebeldeFacade.save(rebeldeDTO);
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

}