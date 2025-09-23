package com.meuseventos.confirmacao_presenca_api.domain.service.participante.listar;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.participantes.ParticipanteRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarListagemPaginadaParticipantesOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.ParticipanteEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarListagemPaginadaParticipantesService {

    private final ParticipanteRepository repository;
    private final EventoRepository eventoRepository;

    public Page<BuscarListagemPaginadaParticipantesOutputDTO> listarParticipantes(
            String codigo, int pagina, int tamanho, String filtro) {

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataCriacao").descending());

        EventoEntity evento = eventoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException(
                        "O código do evento informado não pertence a um evento."));

        Page<ParticipanteEntity> paginaParticipante;

        if (filtro != null && !filtro.isBlank()) {
            paginaParticipante = repository.searchByEventoAndFiltro(evento.getId(), filtro.trim(), pageable);
        } else {
            paginaParticipante = repository.findByEventoId(evento.getId(), pageable);
        }

        return paginaParticipante.map(p -> new BuscarListagemPaginadaParticipantesOutputDTO(
                p.getId(),
                p.getNome(),
                p.getCpf()
        ));
    }
}
