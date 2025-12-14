package br.org.gam.api.Entities.member.services.getMember;

import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.services.getAccount.GetAccount;
import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMember implements GetMember {
    private final GetMemberInstance getMemberInstance;
    private final MemberMapper memberMapper;
    private final AccountMapper accountMapper;
    private final GetAccount getAccount;

    public SpringGetMember(GetMemberInstance getMemberInstance, MemberMapper memberMapper, AccountMapper accountMapper, GetAccount getAccount) {
        this.getMemberInstance = getMemberInstance;
        this.memberMapper = memberMapper;
        this.accountMapper = accountMapper;
        this.getAccount = getAccount;
    }

    @Override
    public GetMemberRDTO byId(UUID id) {

        MemberEntity memberEntity = getMemberInstance.entityById(id);

        int age = memberMapper.fromEntityToDomain(memberEntity).getAge();

        GetAccountRDTO accountRDTO = getAccount.byId(memberEntity.getAccount().getId());

        return memberMapper.fromEntityToGetMemberRDTO(memberEntity, age, accountRDTO);
    }
}
