package com.meuseventos.confirmacao_presenca_api.application.config;

import com.meuseventos.confirmacao_presenca_api.domain.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    //  A requisição é uma só, mas antes de chegar no controller ela passa por uma sequência de filtros.
    //  O método doFilterInternal recebe esse filterChain justamente pra você deixar a requisição seguir para os próximos filtros (ou pro controller, se não tiver mais filtros).
    // filterChain.doFilter(request, response);
    // Isso significa: "já fiz minha parte nesse filtro, pode continuar a request no fluxo normal".
    // Se você não chamar o filterChain.doFilter(...), a requisição para ali mesmo e nunca chega no controller (nem nos outros filtros).
    // Isso é usado, por exemplo, quando você quer bloquear a requisição (ex: token inválido → retorna 401 e não deixa continuar).
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Ignora as rotas públicas
        if (path.startsWith("/auth") || path.startsWith("/eventos")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtService.extrairUsuario(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = new User(username, "", Collections.emptyList());
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Sua sessão expirou, efetue o login novamente." );
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido");
        }
    }

}
