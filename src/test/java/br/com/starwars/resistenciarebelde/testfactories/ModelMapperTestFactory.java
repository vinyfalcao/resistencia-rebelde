package br.com.starwars.resistenciarebelde.testfactories;

import br.com.starwars.resistenciarebelde.converters.CreateRebeldeDTOEntityConverter;
import br.com.starwars.resistenciarebelde.converters.RebeldeEntityCreateRebeldeDTOConverter;
import org.modelmapper.ModelMapper;

public class ModelMapperTestFactory {

    private ModelMapperTestFactory(){}

    public static ModelMapper getModelMapperInstance(){
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new CreateRebeldeDTOEntityConverter());
        modelMapper.addConverter(new RebeldeEntityCreateRebeldeDTOConverter());
        return modelMapper;
    }

}
