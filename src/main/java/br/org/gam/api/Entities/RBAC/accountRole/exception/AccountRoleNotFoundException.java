package br.org.gam.api.Entities.RBAC.accountRole.exception;

public class AccountRoleNotFoundException extends RuntimeException {
    public AccountRoleNotFoundException(String message) {
        super(message);
    }
}
