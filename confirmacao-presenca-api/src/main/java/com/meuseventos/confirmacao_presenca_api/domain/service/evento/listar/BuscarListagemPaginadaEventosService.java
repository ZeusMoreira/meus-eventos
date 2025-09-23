package com.meuseventos.confirmacao_presenca_api.domain.service.evento.listar;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.participantes.ParticipanteRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarListagemPaginadaEventosOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarListagemPaginadaEventosService {

    private final EventoRepository repository;

    private final ParticipanteRepository participanteRepository;

    public Page<BuscarListagemPaginadaEventosOutputDTO> listarEventos(int pagina, int tamanho, String filtro) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataEvento").descending());

        Page<EventoEntity> paginaEvento;

        if (filtro != null && !filtro.isBlank()) {
            paginaEvento = repository.findByTituloContainingIgnoreCase(filtro, pageable);
        } else {
            paginaEvento = repository.findAll(pageable);
        }

        List<UUID> idsEventos = paginaEvento.getContent()
                .stream()
                .map(EventoEntity::getId)
                .toList();

        List<Object[]> resultados = participanteRepository.contarParticipantesPorEventoIds(idsEventos);

        Map<UUID, Long> participantesPorEvento = resultados.stream()
                .collect(Collectors.toMap(
                        r -> (UUID) r[0],
                        r -> (Long) r[1]
                ));

        return paginaEvento.map(evento -> new BuscarListagemPaginadaEventosOutputDTO(
                evento.getTitulo(),
                evento.getDataEvento(),
                participantesPorEvento.getOrDefault(evento.getId(), 0L).intValue(),
                evento.getCodigo()
        ));
    }

}