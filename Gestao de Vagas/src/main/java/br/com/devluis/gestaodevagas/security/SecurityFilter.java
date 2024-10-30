package br.com.devluis.gestaodevagas.security;

import br.com.devluis.gestaodevagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        //SecurityContextHolder.clearContext(); //SecurityContextHolder armazena dados da requisicao como por exemplo as informacoes de autenticacao, para isso devemos limpar a cada requisicao
        String header = request.getHeader("Authorization"); //Recupera o que está dentro do Authorization(AQUI CONTEM O BEARER TOKEN)
        if (request.getRequestURI().startsWith("/company")){// essa linha verifica se a requisicao comeca com /company para qu o filtro especifico possa ser aplicado, se nao for ele vai passar para outro filtro

            if (header != null){// TESTANDO SE NÃO É NULO
                var token = this.jwtProvider.validateToken(header); //COMO HEADER NAO É NULO É FEITO A VALIDACAO DO TOKEN

                if (token == null){ //TESTANDO O CAMPO É VAZIO
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // SE FOR VAZIO ELE VAI SETAR O STATUS COMO NAO AUTORIZADO
                    return;
                }
                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())
                ).toList();
                request.setAttribute("company_id", token.getSubject()); //adiciona o company_id como atributo vindo de subject que no qual ele que está sendo autorizado
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth); //guarda as informações de login apresentadas no auth
            }
        }

        filterChain.doFilter(request, response); //
    }
}