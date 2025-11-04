package br.org.gam.api.Entities.account.services.createAccount.service;

import br.org.gam.api.Entities.account.common.IAccountMapper;
import br.org.gam.api.Entities.account.domain.Account;
import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountDTO;
import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountResponseDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements ICreateAccountService{

    private final IAccountRepository accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final IAccountMapper accountMapper;

    public CreateAccountService(IAccountRepository accountRepo, PasswordEncoder passwordEncoder, IAccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
    }

    @Transactional
    @Override
    public CreateAccountResponseDTO createAccount(CreateAccountDTO dto) {
        String hashedPassword = passwordEncoder.encode(dto.password());

        Account newAccount = new Account(dto.email(), hashedPassword, dto.displayName());
        AccountEntity newEntity = accountMapper.fromDomainToEntity(newAccount);
        AccountEntity savedEntity = accountRepo.save(newEntity);

        return accountMapper.fromEntityToCreateAccountResponseDTO(savedEntity);
    }
}
