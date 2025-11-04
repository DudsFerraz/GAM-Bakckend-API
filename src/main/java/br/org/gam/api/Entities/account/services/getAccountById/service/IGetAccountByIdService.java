package br.org.gam.api.Entities.account.services.getAccountById.service;

import br.org.gam.api.Entities.account.services.getAccountById.dto.GetAccountByIdDTO;

import java.util.UUID;

public interface IGetAccountByIdService {
    GetAccountByIdDTO getAccountById(UUID id);
}
