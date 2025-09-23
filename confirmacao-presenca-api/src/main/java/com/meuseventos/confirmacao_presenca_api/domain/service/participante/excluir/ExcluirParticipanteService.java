package com.meuseventos.confirmacao_presenca_api.domain.service.participante.excluir;


import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.participantes.ParticipanteRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.ParticipanteInputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.ParticipanteEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.validation.ParticipanteValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcluirParticipanteService {

    private final ParticipanteRepository repository;

    private final EventoRepository eventoRepository;

    private final ParticipanteValidator validator;

    @Transactional
    public void excluir(String codigo, String id) {
        EventoEntity evento = buscarEventoPorCodigo(codigo);

        UUID idParticipante = validator.validarExistenciaParticipanteNoEvento(evento.getId(), id);

        repository.deleteByIdAndEventoId(idParticipante, evento.getId());
    }

    private EventoEntity buscarEventoPorCodigo(String codigo) {
        Optional<EventoEntity> evento = eventoRepository.findByCodigo(codigo);

        if(evento.isEmpty()) {
            throw new NaoEncontradoException("Evento não encontrado.");
        }

        return evento.get();
    }
}