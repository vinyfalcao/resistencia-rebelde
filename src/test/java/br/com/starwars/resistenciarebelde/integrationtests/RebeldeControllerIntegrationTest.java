package br.com.starwars.resistenciarebelde.integrationtests;

import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.LocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RegistroTraicaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RebeldeControllerIntegrationTest {

    public static final String REBELDES = "/rebeldes";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RebeldeRepository rebeldeRepository;
    @Autowired
    private RegistroTraicaoRepository registroTraicaoRepository;
    @Autowired
    private RebeldeFacade rebeldeFacade;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        registroTraicaoRepository.deleteAll();
        rebeldeRepository.deleteAll();
    }

    @Test
    public void shouldFindAll() throws Exception {
        final var expectedRebeldeInstance = rebeldeRepository.save(generateRebeldeInstance());
        final var expectedJson = new ObjectMapper().writeValueAsString(Collections.singletonList(toRebeldeDTO(expectedRebeldeInstance)));

        this.mockMvc.perform(get(REBELDES))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void shouldCreateNewRebelde() throws Exception{
        final RebeldeEntity expectedRebelde = generateRebeldeInstance();
        final var requestBody = new ObjectMapper().writeValueAsString(expectedRebelde);

        this.mockMvc.perform(post(REBELDES).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(expectedRebelde.getNome()))
                .andExpect(jsonPath("$.genero").value(expectedRebelde.getGenero()))
                .andExpect(jsonPath("$.idade").value(expectedRebelde.getIdade())
                //.andExpect(jsonPath("$.id").isNotEmpty()
                );
    }

    @Test
    public void shouldUpdateRebelde() throws Exception {
        final var expectedRebeldeInstance = rebeldeRepository.save(generateRebeldeInstance());
        expectedRebeldeInstance.setNome("Novo Nome");
        final var requestBody = new ObjectMapper().writeValueAsString(expectedRebeldeInstance);
        this.mockMvc.perform(put(REBELDES).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNoContent());

        final var result = rebeldeRepository.findById(expectedRebeldeInstance.getId()).get();
        assertThat(result.getNome()).isEqualTo(expectedRebeldeInstance.getNome());
    }

    @Test
    public void shouldUpdateLocalizacaoRebelde() throws Exception {
        final var expectedRebeldeInstance = rebeldeRepository.save(generateRebeldeInstance());
        final UpdateLocalizacaoRebeldeDTO dto = generateLocalizacaoRebeldeDTO(expectedRebeldeInstance.getId());
        final var requestBody = new ObjectMapper().writeValueAsString(dto);

        this.mockMvc.perform(patch(REBELDES).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNoContent());

        final CreateRebeldeDTO result = rebeldeFacade.findById(expectedRebeldeInstance.getId());
        dto.getLocalizacaoRebeldeDto().setId(result.getLocalizacaoRebeldeDTO().getId());
        assertThat(result.getLocalizacaoRebeldeDTO()).isEqualTo(dto.getLocalizacaoRebeldeDto());
    }


    private RebeldeEntity generateRebeldeInstance() {
        final RebeldeEntity rebeldeEntity = new RebeldeEntity();
        rebeldeEntity.setNome("Teste teste");
        rebeldeEntity.setGenero("Teste");
        rebeldeEntity.setIdade(500L);
        rebeldeEntity.setInventario(Collections.emptyList());
        return rebeldeEntity;
    }

    private UpdateLocalizacaoRebeldeDTO generateLocalizacaoRebeldeDTO(final Long idRebelde){
        UpdateLocalizacaoRebeldeDTO dto = new UpdateLocalizacaoRebeldeDTO();
        dto.setIdRebelde(idRebelde);
        final var localizacao = new LocalizacaoRebeldeDTO();
        localizacao.setNomeGalaxia("Galaxia");
        localizacao.setLatitude(10.0);
        localizacao.setLongitude(-10.0);
        dto.setLocalizacaoRebeldeDto(localizacao);
        return dto;
    }

    private CreateRebeldeDTO toRebeldeDTO(final RebeldeEntity rebeldeEntity){
        var createRebeldeDTO = modelMapper.map(rebeldeEntity, CreateRebeldeDTO.class);
        if(rebeldeEntity.getLocalizacao() != null){
            createRebeldeDTO.setLocalizacaoRebeldeDTO(modelMapper.map(rebeldeEntity.getLocalizacao(), LocalizacaoRebeldeDTO.class));
        }
        return createRebeldeDTO;
    }
}