package br.com.starwars.resistenciarebelde.integrationtests;


import br.com.starwars.resistenciarebelde.dtos.ItemDTO;
import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.repositories.ItemInventarioRepository;
import br.com.starwars.resistenciarebelde.repositories.ItemRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RegistroTraicaoRepository;
import br.com.starwars.resistenciarebelde.testfactories.ItemEntityTestFactory;
import br.com.starwars.resistenciarebelde.testfactories.ModelMapperTestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.util.Collections.singletonList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

    public static final String ITEMS = "/items";
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RebeldeRepository rebeldeRepository;
    @Autowired
    private RegistroTraicaoRepository registroTraicaoRepository;
    @Autowired
    private ItemInventarioRepository itemInventarioRepository;
    @Autowired
    private MockMvc mockMvc;

    private ModelMapper modelMapper = ModelMapperTestFactory.getModelMapperInstance();

    @BeforeEach
    void setUp() {
        itemInventarioRepository.deleteAllInBatch();
        itemRepository.deleteAll();
        registroTraicaoRepository.deleteAll();
        rebeldeRepository.deleteAll();
    }

    @Test
    public void shouldFindAllItems() throws Exception {
        final ItemEntity itemEntity = ItemEntityTestFactory.generateItemInstance().build();
        final ItemEntity expectedItemEntity = itemRepository.save(itemEntity);
        final var expectedJson = new ObjectMapper().writeValueAsString(singletonList(modelMapper.map(expectedItemEntity, ItemDTO.class)));

        MvcResult mvcResult = this.mockMvc.perform(get(ITEMS))
                .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void shouldSaveNewItem() throws Exception {
        final ItemEntity itemEntity = ItemEntityTestFactory.generateItemInstance().build();
        final var requestBody = new ObjectMapper().writeValueAsString(modelMapper.map(itemEntity, ItemDTO.class));

        MvcResult mvcResult = this.mockMvc.perform(post(ITEMS)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(itemEntity.getNome()))
                .andExpect(jsonPath("$.pontos").value(itemEntity.getPontos()));
    }

}
