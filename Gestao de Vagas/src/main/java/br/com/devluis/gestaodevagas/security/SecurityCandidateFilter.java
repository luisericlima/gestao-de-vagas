package br.com.devluis.gestaodevagas.security;

import br.com.devluis.gestaodevagas.providers.JWTCandidateProvider;
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

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {
    @Autowired
    private JWTCandidateProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");
        if (request.getRequestURI().startsWith("/candidate"))//essa linha verifica se a requisicao comeca com /candidate para qu o filtro especifico possa ser aplicado, se nao for ele vai passar para outro filtro
        {
            if (header != null) {
                var token = this.jwtProvider.validateToken(header);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                request.setAttribute("candidate_id", token.getSubject());

                var roles = token.getClaim("roles").asList(Object.class); //convete arrays de roles(permissoes) para um arrays de objeto, geralmente é utilizado para verificar se possui uma tag, tendo essa tag autoriza alguma rota ou operacao

                var grants = roles.stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())//role tem que ser String, o //O SimpleGrantedAuthority é a classe que o Spring Security usa para representar as autorizações (permissões ou papéis) de um usuário
                ).toList();                                     //USO DO ROLE_ é umaconvenção comum no Spring Security, que espera que as roles dos usuários sejam prefixados com ROLE_

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response); //Usado depois de implementar a logica, tem como funcao liberar a requisicao para o proximo filtro ou controller
    }
}
