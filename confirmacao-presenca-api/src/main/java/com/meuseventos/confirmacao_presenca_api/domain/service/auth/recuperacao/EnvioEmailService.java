package com.meuseventos.confirmacao_presenca_api.domain.service.auth.recuperacao;

import com.meuseventos.confirmacao_presenca_api.adapter.gateway.dataprovider.adminUser.AdminUserRepository;
import com.meuseventos.confirmacao_presenca_api.domain.entity.entities.AdminUserEntity;
import com.meuseventos.confirmacao_presenca_api.domain.exception.CredenciaisInvalidasException;
import com.meuseventos.confirmacao_presenca_api.domain.exception.NaoEncontradoException;
import com.meuseventos.confirmacao_presenca_api.domain.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnvioEmailService {

    private final AdminUserRepository repository;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Value("${app.frontend-url}")
    private String url;

    public void solicitarResetSenha() {
        AdminUserEntity usuario = repository.findByEmail(adminEmail)
                .orElseThrow(() -> new NaoEncontradoException("Usuário admin não encontrado"));

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setResetExpiracao(LocalDateTime.now().plusMinutes(15));
        repository.save(usuario);

        String link = url + "/resetar-senha?token=" + token;
        emailService.enviarEmail(usuario.getEmail(),
                "Redefinição de senha",
                "Clique no link para redefinir sua senha: " + link);
    }
}

