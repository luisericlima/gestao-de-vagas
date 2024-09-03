package br.com.devluis.gestaodevagas.modules.candidate.exceptions;


public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuario Já existe!");
    }
}
