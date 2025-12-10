package br.org.gam.api.Entities.RBAC.accountRole.exception;

public class AccountAlreadyHasRoleException extends RuntimeException {
    public AccountAlreadyHasRoleException(String message) {
        super(message);
    }
}
