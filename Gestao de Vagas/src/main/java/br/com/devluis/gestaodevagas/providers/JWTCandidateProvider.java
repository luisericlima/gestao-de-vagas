package br.com.devluis.gestaodevagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCandidateProvider {
    @Value("${security.token.secret.candidate}")
    private String secretKey;
    public DecodedJWT validateToken(String token) { //TIPO DECODED RETORNA O TIPO DE TOKEN E O ALGORITMO DE ASSINATURA, O PAYLOAD E O SIGNATURE
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        try {//Se token estiver expirado ou invalido ele retorna erro, por isso o trycatch
            var tokenDecoded = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
