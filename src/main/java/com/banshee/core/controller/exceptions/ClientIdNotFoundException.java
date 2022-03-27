package com.banshee.core.controller.exceptions;

public class ClientIdNotFoundException extends AttributeNotFoundException {

    public ClientIdNotFoundException(Long id) {
        super("Couldn't find Client with Id: " + id);
    }
}
