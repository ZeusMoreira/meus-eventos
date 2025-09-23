package com.meuseventos.confirmacao_presenca_api.domain.service.evento.listar;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarEventoPorCodigoOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarListagemPaginadaParticipantesOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.service.participante.listar.BuscarListagemPaginadaParticipantesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuscarEventoPorCodigoService {

    private final BuscarListagemPaginadaParticipantesService buscarListagemPaginadaParticipantesService;

    private final EventoRepository repository;

    public BuscarEventoPorCodigoOutputDTO listarEvento(String codigo, int page, int size, String filtro) {
        Optional<EventoEntity> evento = repository.findByCodigo(codigo);

        if(evento.isEmpty()) {
            throw new NaoEncontradoException("Evento não encontrado.");
        }

        Page<BuscarListagemPaginadaParticipantesOutputDTO> paginaParticipantes = buscarListagemPaginadaParticipantesService.listarParticipantes(evento.get().getCodigo(), page, size, filtro);

        return new BuscarEventoPorCodigoOutputDTO(
                evento.get().getTitulo(),
                evento.get().getCodigo(),
                paginaParticipantes
        );
    }

}