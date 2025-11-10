package br.org.gam.api.Entities.member.services.registerMember;

import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.services.getAccountInstance.IGetAccountInstanceService;
import br.org.gam.api.Entities.member.IMemberMapper;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.exception.MemberAccountConflictException;
import br.org.gam.api.Entities.member.persistence.IMemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.common.Name;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RegisterMemberService implements IRegisterMemberService {

    private final IMemberRepository memberRepo;

    private final IMemberMapper memberMapper;
    private final IGetAccountInstanceService getAccountInstanceService;

    public RegisterMemberService(IMemberRepository memberRepo, IMemberMapper memberMapper, IGetAccountInstanceService getAccountInstanceService) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
        this.getAccountInstanceService = getAccountInstanceService;
    }

    @Transactional
    @Override
    public RegisterMemberResponseDTO registerMember(RegisterMemberDTO dto) {
        if (memberRepo.existsByAccountId(dto.accountId())){
            throw new MemberAccountConflictException("A member is already linked to this account.");
        }

        Account relatedAccount = getAccountInstanceService.getAccountDomainById(dto.accountId());

        Name name = new Name(dto.firstName(), dto.surname());

        Member newMember = Member.register(relatedAccount, name, dto.birthDate(), dto.phoneNumber());

        MemberEntity newMemberEntity = memberMapper.fromDomainToEntity(newMember);
        MemberEntity savedMemberEntity = memberRepo.save(newMemberEntity);

        return memberMapper.fromEntityToRegisterMemberResponseDTO(savedMemberEntity);
    }
}
