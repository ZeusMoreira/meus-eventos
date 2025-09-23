package com.meuseventos.confirmacao_presenca_api.adapter.entrypoint.controller.evento.qrcode;

import com.meuseventos.confirmacao_presenca_api.domain.service.evento.qrcode.QrCodeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/eventos")
public class GerarQrCodeController {

    private final QrCodeService service;

    @GetMapping("/{codigo}/qr-code")
    public ResponseEntity<Object> getQrCode(@PathVariable String codigo) {
        String output = service.executar(codigo);

        return ResponseEntity.ok().body(Map.of("qrCode", output));
    }
}
