package br.com.devluis.gestaodevagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean //identifica uma sobreescrição(entre aspas) de um objeto já gerenciado pelo SPRING
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //SOBREESCRITA DO METODO DESSA FORMA ESTAMOS COLOCANDO NOSSA PROPRIA CONFIGURACAO PARAM ESTE METODO
        http.csrf(csrf -> csrf.disable()) //desabilita a proteção contra CSRF (Cross-Site Request Forgery)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidate/").permitAll()
                            .requestMatchers("/company/").permitAll()
                            .requestMatchers("/auth/company").permitAll()
                            .requestMatchers("/candidate/auth").permitAll();

                    auth.anyRequest().authenticated();
                }).addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {//CRIACAO PARA DEFINIR O TIPO DE ENCODER, FOI UTILIZADO O BCRYPT
        return new BCryptPasswordEncoder();
    }
}
