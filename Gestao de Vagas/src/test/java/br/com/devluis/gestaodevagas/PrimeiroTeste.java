package br.com.devluis.gestaodevagas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PrimeiroTeste {

    @Test
    public void deveSerPossivelCalcularDoisNumeros(){
        var result = calculate(2,3);
        assertNotEquals(result, 4);
    }

    public static int calculate(int num1, int num2) {
        return num1 + num2;
    }

}
