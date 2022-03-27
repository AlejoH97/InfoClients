package com.banshee.core.controller.exceptions;

public class SalesRepresentativeNotFoundException extends AttributeNotFoundException {

    public SalesRepresentativeNotFoundException(Long id) {
        super("Couldn't find Representative with Id: " + id);
    }
}
