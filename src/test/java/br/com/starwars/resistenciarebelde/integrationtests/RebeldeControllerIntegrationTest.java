package br.com.starwars.resistenciarebelde.integrationtests;

import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.LocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.UpdateRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.ItemInventarioEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.facades.RebeldeFacade;
import br.com.starwars.resistenciarebelde.repositories.ItemInventarioRepository;
import br.com.starwars.resistenciarebelde.repositories.ItemRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RegistroTraicaoRepository;
import br.com.starwars.resistenciarebelde.testfactories.ItemEntityTestFactory;
import br.com.starwars.resistenciarebelde.testfactories.RebeldeEntityTestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockAsyncContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemInventarioRepository itemInventarioRepository;

    @BeforeEach
    public void setUp(){
        itemInventarioRepository.deleteAllInBatch();
        itemRepository.deleteAll();
        registroTraicaoRepository.deleteAll();
        rebeldeRepository.deleteAll();
    }

    @Test
    public void shouldFindAll() throws Exception {
        final RebeldeEntity rebeldeEntity = generateRebeldeInstance();
        final var expectedRebeldeInstance = rebeldeRepository.save(rebeldeEntity);
        final var expectedJson = new ObjectMapper().writeValueAsString(Collections.singletonList(toRebeldeDTO(expectedRebeldeInstance)));

        MvcResult mvcResult = this.mockMvc.perform(get(REBELDES))
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void shouldFindById() throws Exception{
        final var expectedRebelde = rebeldeRepository.save(generateRebeldeInstance());

        MvcResult mvcResult = this.mockMvc.perform(get(REBELDES + "/" + expectedRebelde.getId()))
                .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(expectedRebelde.getNome()))
                .andExpect(jsonPath("$.genero").value(expectedRebelde.getGenero()))
                .andExpect(jsonPath("$.idade").value(expectedRebelde.getIdade()));
    }

    @Test
    public void shouldCreateNewRebelde() throws Exception{
        RebeldeEntity expectedRebelde = generateRebeldeInstance();
        final var createRebeldeDTO = toRebeldeDTO(expectedRebelde);
        final var requestBody = new ObjectMapper().writeValueAsString(createRebeldeDTO);

        this.mockMvc.perform(post(REBELDES).content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(expectedRebelde.getNome()))
                .andExpect(jsonPath("$.genero").value(expectedRebelde.getGenero()))
                .andExpect(jsonPath("$.idade").value(expectedRebelde.getIdade()))
                .andExpect(jsonPath("$.itemInventarioRebeldeDTO[0].itemId").value(expectedRebelde.getInventario().get(0).getItem().getId()));
    }

    @Test
    public void shouldUpdateRebelde() throws Exception {
        final var expectedRebeldeInstance = rebeldeRepository.save(generateRebeldeInstance());
        expectedRebeldeInstance.setNome("Novo Nome");
        final var updateRebeldeDto = modelMapper.map(expectedRebeldeInstance, UpdateRebeldeDTO.class);
        final var requestBody = new ObjectMapper().writeValueAsString(updateRebeldeDto);
        this.mockMvc.perform(put(REBELDES).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isNoContent());

        final var result = rebeldeRepository.findById(expectedRebeldeInstance.getId()).get();
        assertThat(result.getNome()).isEqualTo(expectedRebeldeInstance.getNome());
    }

    @Disabled
    @Test
    public void shouldUpdateLocalizacaoRebelde() throws Exception {
        final var expectedRebeldeInstance = rebeldeRepository.save(generateRebeldeInstance());
        final UpdateLocalizacaoRebeldeDTO dto = generateLocalizacaoRebeldeDTO(expectedRebeldeInstance.getId());
        final var requestBody = new ObjectMapper().writeValueAsString(dto);

        var mvcResult = this.mockMvc.perform(patch(REBELDES).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andReturn();

        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent());

        Thread.sleep(5000);

        final CreateRebeldeDTO result = rebeldeFacade.findById(expectedRebeldeInstance.getId()).get();
        dto.getLocalizacaoRebeldeDto().setId(result.getLocalizacaoRebeldeDTO().getId());
        assertThat(result.getLocalizacaoRebeldeDTO()).isEqualTo(dto.getLocalizacaoRebeldeDto());

    }


    private RebeldeEntity generateRebeldeInstance() {
        final var item = itemRepository.save(ItemEntityTestFactory.generateItemInstance().build());
        final var inventario = Collections.singletonList(new ItemInventarioEntity(null, item, null, 1L));

        final RebeldeEntity rebeldeEntity = RebeldeEntityTestFactory.aRebeldeEntity()
                .withId(null)
                .withNome("Teste teste")
                .withGenero("Teste")
                .withIdade(500L)
                .withInventario(inventario)
                .build();
        inventario.get(0).setRebelde(rebeldeEntity);
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
        return createRebeldeDTO;
    }
}