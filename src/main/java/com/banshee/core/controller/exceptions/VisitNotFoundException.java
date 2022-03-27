package com.banshee.core.controller.exceptions;

public class VisitNotFoundException extends AttributeNotFoundException {

    public VisitNotFoundException(Long id) {
        super("Couldn't find Visit with Id: " + id);
    }
}
