package com.banshee.core.controller.exceptions;

public class LocationNotFoundException extends AttributeNotFoundException {

    public LocationNotFoundException(Long id) {
        super("Couldn't find Location with Id: " + id);
    }
}
