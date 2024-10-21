package br.com.devluis.gestaodevagas.exceptions;


public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuario Já existe!");
    }
}
