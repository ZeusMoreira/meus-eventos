package com.meuseventos.confirmacao_presenca_api.domain.service.evento.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.eventos.EventoRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.EventoEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.QrCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final EventoRepository eventoRepository;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public String executar(String codigo) {
        EventoEntity evento = eventoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new NaoEncontradoException("Evento não encontrado."));

        try {
            String url = frontendUrl + "/home/" + evento.getCodigo();
            byte[] qrBytes = generateQrCode(url, 250, 250);

            return "data:image/png;base64," + Base64.getEncoder().encodeToString(qrBytes);

        } catch (Exception e) {
            throw new QrCodeException("Erro ao gerar o QR Code.");
        }
    }

    private byte[] generateQrCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
