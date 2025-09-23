package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.evento;

import com.meuseventos.confirmacao_presenca_api.domain.entity.dto.BuscarEventoPorCodigoOutputDTO;
import com.meuseventos.confirmacao_presenca_api.domain.service.evento.listar.BuscarEventoPorCodigoService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/eventos")
@RequiredArgsConstructor
public class BuscarEventoPorCodigoController {

    private final BuscarEventoPorCodigoService service;

    @GetMapping("/{codigo}")
    public ResponseEntity<BuscarEventoPorCodigoOutputDTO> listarEvento(@PathVariable("codigo") @NotBlank String codigo,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size,
                                                                       @RequestParam(required = false) String filtro) {

        BuscarEventoPorCodigoOutputDTO evento = service.listarEvento(codigo, page, size, filtro);

        return ResponseEntity.ok(evento);
    }

}