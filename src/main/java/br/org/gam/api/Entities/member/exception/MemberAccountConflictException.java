package br.org.gam.api.Entities.member.exception;

public class MemberAccountConflictException extends RuntimeException {
    public MemberAccountConflictException(String message) {
        super(message);
    }
}
