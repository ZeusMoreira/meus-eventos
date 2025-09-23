package com.meuseventos.confirmacao_presenca_api.domain.service.participante.criar;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.participantes.ParticipanteRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.ParticipanteInputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.ParticipanteEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.validation.ParticipanteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CriarParticipanteService {

    private final ParticipanteRepository repository;

    private final EventoRepository eventoRepository;

    private final ParticipanteValidator validator;

    public ParticipanteEntity salvar(ParticipanteInputDTO dto, String codigo) {
        EventoEntity evento = buscarEventoPorCodigo(codigo);

        validator.validar(dto.getCpf(), evento);

        ParticipanteEntity participante = ParticipanteEntity.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .evento(evento)
                .build();

        return repository.save(participante);
    }

    private EventoEntity buscarEventoPorCodigo(String codigo) {
        Optional<EventoEntity> evento = eventoRepository.findByCodigo(codigo);

        if(evento.isEmpty()) {
            throw new NaoEncontradoException("Evento não encontrado.");
        }

        return evento.get();
    }
}
