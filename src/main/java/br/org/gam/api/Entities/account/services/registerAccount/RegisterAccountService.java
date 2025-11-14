package br.org.gam.api.Entities.account.services.registerAccount;

import br.org.gam.api.Entities.account.IAccountMapper;
import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.exception.AccountConflictException;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterAccountService implements IRegisterAccountService {

    private final IAccountRepository accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final IAccountMapper accountMapper;

    public RegisterAccountService(IAccountRepository accountRepo, PasswordEncoder passwordEncoder, IAccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
    }

    @Transactional
    @Override
    public RegisterAccountResponseDTO registerAccount(RegisterAccountDTO dto) {
        if (accountRepo.existsByEmail(dto.email())){
            throw new AccountConflictException("Email '" + dto.email() + "' already registered.");
        }

        String hashedPassword = passwordEncoder.encode(dto.password());

        Account newAccount = Account.register(dto.email(), hashedPassword, dto.displayName());
        AccountEntity newAccountEntity = accountMapper.fromDomainToEntity(newAccount);
        AccountEntity savedAccountEntity = accountRepo.save(newAccountEntity);

        return accountMapper.fromEntityToRegisterAccountResponseDTO(savedAccountEntity);
    }
}
