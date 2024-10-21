package br.com.devluis.gestaodevagas.security;

import br.com.devluis.gestaodevagas.providers.JWTProvider;
import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        String header = request.getHeader("Authorization"); //Recupera o que está dentro do Authorization(AQUI CONTEM O BEARER TOKEN)
        System.out.println("HEADER AQUI " + header);
        if (header != null){// TESTANDO SE NÃO É NULO
            var subject = this.jwtProvider.validateToken(header); //COMO HEADER NAO É NULO É FEITO A VALIDACAO DO TOKEN
            if (subject.isEmpty()){ //TESTANDO O CAMPO É VAZIO
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // SE FOR VAZIO ELE VAI SETAR O STATUS COMO NAO AUTORIZADO
                return;
            }
            request.setAttribute("company_id", subject); //adiciona o company_id como atributo vindo de subject que no qual ele que está sendo autorizado
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth); //guarda as informações de login apresentadas no auth
        }
        filterChain.doFilter(request, response); //
    }
}
