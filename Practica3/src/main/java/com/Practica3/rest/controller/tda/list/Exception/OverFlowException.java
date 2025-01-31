package com.Practica3.rest.controller.tda.list.Exception;

public class OverFlowException extends Exception {

    /**
     * Constructor vacío para OverFlowException.
     */
    public OverFlowException() {
        super(); // Llama al constructor vacío de Exception
    }

    /**
     * Constructor que acepta un mensaje.
     * @param msg el mensaje detallado
     */
    public OverFlowException(String msg) {
        super(msg); // Llama al constructor de Exception con un mensaje
    }
}
