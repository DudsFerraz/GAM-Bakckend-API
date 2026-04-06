package br.org.gam.api.Entities.events.missa.exception;

public class MissaNotFoundException extends RuntimeException {
  public MissaNotFoundException(String message) {
    super(message);
  }
}
