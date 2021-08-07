package br.com.starwars.resistenciarebelde.consumers;

import br.com.resistenciarebelde.itemsinventario.model.Item;
import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.services.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemEventConsumerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemEventConsumer itemEventConsumer;

    @Test
    public void whenReceiveItemEventShouldSaveNewItem(){
        final Item expectedItem = new Item(UUID.randomUUID(), "Teste Nome", 10L);
        itemEventConsumer.handleMessage(expectedItem);
        verify(itemService).save(new ItemEntity(null,
                expectedItem.getUuid(),
                expectedItem.getNome(),
                expectedItem.getPontos())
        );
    }

}