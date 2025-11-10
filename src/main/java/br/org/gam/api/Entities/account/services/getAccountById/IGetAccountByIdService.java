package br.org.gam.api.Entities.account.services.getAccountById;

import java.util.UUID;

public interface IGetAccountByIdService {
    GetAccountByIdDTO getAccountById(UUID id);
}
