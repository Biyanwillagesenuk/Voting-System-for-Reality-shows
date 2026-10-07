package com.votingsystem.for_reality_shows.exception;

/** Thrown when a ticket id / reference does not exist (mapped to HTTP 404). */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
