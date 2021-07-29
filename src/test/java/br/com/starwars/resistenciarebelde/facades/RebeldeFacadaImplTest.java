package br.com.starwars.resistenciarebelde.facades;

import br.com.starwars.resistenciarebelde.dtos.LocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.dtos.CreateRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.LocalizacaoRebeldeEntity;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.facades.impl.RebeldeFacadeImpl;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RebeldeFacadaImplTest {

    @Mock
    private RebeldeService rebeldeService;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private RebeldeFacadeImpl rebeldeFacadeImpl;

    @Test
    public void shouldFindAllRebeldes(){
        final List<RebeldeEntity> mockedEntityList = singletonList(generateRebeldeInstance());
        when(rebeldeService.findAll()).thenReturn(mockedEntityList);

        final List<CreateRebeldeDTO> expectedResult = toDTO(mockedEntityList);
        final List<CreateRebeldeDTO> result = rebeldeFacadeImpl.findAll();
        assertThat(result).isEqualTo(expectedResult);
    }

    private List<CreateRebeldeDTO> toDTO(final List<RebeldeEntity> rebeldeEntities){
        return rebeldeEntities.stream()
                .map(this::toRebeldeDTO)
                .collect(toList());
    }
    private CreateRebeldeDTO toRebeldeDTO(final RebeldeEntity rebeldeEntity){
        var createRebeldeDTO = modelMapper.map(rebeldeEntity, CreateRebeldeDTO.class);
        if(rebeldeEntity.getLocalizacao() != null){
            createRebeldeDTO.setLocalizacaoRebeldeDTO(modelMapper.map(rebeldeEntity.getLocalizacao(), LocalizacaoRebeldeDTO.class));
        }
        return createRebeldeDTO;
    }

    private RebeldeEntity generateRebeldeInstance(){
        return new RebeldeEntity(1L, "Nome Teste", 500L, "Genero",false,
                generateLocalizacaoRebelde(), null, null, null);
    }

    private LocalizacaoRebeldeEntity generateLocalizacaoRebelde() {
        return new LocalizacaoRebeldeEntity(1L, "Galaxia X", 20.0, -20.0, null);
    }

    private LocalizacaoRebeldeEntity toLocalizacaoRebeldeEntity(final LocalizacaoRebeldeDTO localizacaoRebeldeDTO){
        return new LocalizacaoRebeldeEntity(localizacaoRebeldeDTO.getId(),
                localizacaoRebeldeDTO.getNomeGalaxia(),
                localizacaoRebeldeDTO.getLatitude(),
                localizacaoRebeldeDTO.getLongitude(),
                null);
    }

    private LocalizacaoRebeldeDTO toLocalizacaoRebeldeDto(final LocalizacaoRebeldeEntity entity){
        return new LocalizacaoRebeldeDTO(entity.getId(),
                entity.getNomeGalaxia(),
                entity.getLatitude(),
                entity.getLongitude());
    }





}
