package br.com.starwars.resistenciarebelde.consumers;



import br.com.resistenciarebelde.itemsinventario.model.Item;
import br.com.starwars.resistenciarebelde.entities.ItemEntity;
import br.com.starwars.resistenciarebelde.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemEventConsumer {

    private final ItemService itemService;

    @JmsListener(destination = "ItemsRebeldeQueue", containerFactory = "myFactory")
    public void handleMessage(final Item item){
        this.itemService.save(new ItemEntity(null, item.getUuid(), item.getNome(), item.getPontos()));
        throw new RuntimeException("Eita deu ruim");
    }

}
