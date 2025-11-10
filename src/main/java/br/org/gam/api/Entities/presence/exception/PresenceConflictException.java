package br.org.gam.api.Entities.presence.exception;

public class PresenceConflictException extends RuntimeException {
    public PresenceConflictException(String message) {
        super(message);
    }
}
