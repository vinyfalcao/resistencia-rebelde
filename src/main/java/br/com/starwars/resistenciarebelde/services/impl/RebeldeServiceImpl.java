package br.com.starwars.resistenciarebelde.services.impl;

import br.com.starwars.resistenciarebelde.UpdateLocalizacaoRebeldeDTO;
import br.com.starwars.resistenciarebelde.entities.RebeldeEntity;
import br.com.starwars.resistenciarebelde.repositories.LocalizacaoRebeldeRepository;
import br.com.starwars.resistenciarebelde.repositories.RebeldeRepository;
import br.com.starwars.resistenciarebelde.services.RebeldeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebeldeServiceImpl implements RebeldeService {

    private final RebeldeRepository rebeldeRepository;
    private final LocalizacaoRebeldeRepository localizacaoRebeldeRepository;

    @Override
    public List<RebeldeEntity> findAll() {
        return rebeldeRepository.findAll();
    }

    @Override
    public RebeldeEntity findById(final Long id) {
        return rebeldeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rebelde não localizado"));
    }

    @Override
    public RebeldeEntity save(final RebeldeEntity rebeldeEntity) {
        return rebeldeRepository.save(rebeldeEntity);
    }

    @Override
    public void updateLocalizacao(final UpdateLocalizacaoRebeldeDTO localizacaoRebeldeDTO) {
        final var rebelde = findById(localizacaoRebeldeDTO.getIdRebelde());
        final var novaLocalizacao = localizacaoRebeldeDTO.getLocalizacaoRebeldeEntity();
        final var localizacaoOptional = localizacaoRebeldeRepository.findByNomeGalaxiaAndLatitudeAndLongitude(novaLocalizacao.getNomeGalaxia(),
                novaLocalizacao.getLatitude(),
                novaLocalizacao.getLongitude());
        rebelde.setLocalizacao(localizacaoOptional.orElse(novaLocalizacao));
        save(rebelde);
    }

}