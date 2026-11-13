package it.university.exceptions;

public class NoDataFoundException extends Exception {
    public NoDataFoundException(String message) {
        super(message);//passa il messaggio alla classe base 'Exception'
    }
}

