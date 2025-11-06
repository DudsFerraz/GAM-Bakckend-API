package br.org.gam.api.Entities.member.services.registerMember.service;

import br.org.gam.api.Entities.account.common.IAccountMapper;
import br.org.gam.api.Entities.account.domain.Account;
import br.org.gam.api.Entities.account.exception.AccountNotFoundException;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import br.org.gam.api.Entities.member.common.IMemberMapper;
import br.org.gam.api.Entities.member.domain.Member;
import br.org.gam.api.Entities.member.exception.MemberAccountConflictException;
import br.org.gam.api.Entities.member.persistence.IMemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.registerMember.dto.RegisterMemberDTO;
import br.org.gam.api.Entities.member.services.registerMember.dto.RegisterMemberResponseDTO;
import br.org.gam.api.common.Name;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RegisterMemberService implements IRegisterMemberService {

    private final IMemberRepository memberRepo;
    private final IAccountRepository accountRepo;
    private final IMemberMapper memberMapper;
    private final IAccountMapper accountMapper;

    public RegisterMemberService(IMemberRepository memberRepo, IAccountRepository accountRepo, IMemberMapper memberMapper, IAccountMapper accountMapper) {
        this.memberRepo = memberRepo;
        this.accountRepo = accountRepo;
        this.memberMapper = memberMapper;
        this.accountMapper = accountMapper;
    }

    @Transactional
    @Override
    public RegisterMemberResponseDTO registerMember(RegisterMemberDTO dto) {
        if (memberRepo.existsByAccountId(dto.accountId())){
            throw new MemberAccountConflictException("A member is already linked to this account.");
        }

        Account relatedAccount = accountRepo.findById(dto.accountId())
                .map(accountMapper::fromEntityToDomain)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with id " + dto.accountId()));

        Name name = new Name(dto.firstName(), dto.surname());

        Member newMember = Member.register(relatedAccount, name, dto.birthDate(), dto.phoneNumber());

        MemberEntity newMemberEntity = memberMapper.fromDomainToEntity(newMember);
        MemberEntity savedMemberEntity = memberRepo.save(newMemberEntity);

        return memberMapper.fromEntityToRegisterMemberResponseDTO(savedMemberEntity);
    }
}
