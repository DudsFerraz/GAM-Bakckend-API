//package br.org.gam.api.Entities.member.services.activation;
//
//import br.org.gam.api.Entities.account.Account;
//import br.org.gam.api.Entities.account.IAccountMapper;
//import br.org.gam.api.Entities.account.persistence.AccountEntity;
//import br.org.gam.api.Entities.account.persistence.IAccountRepository;
//import br.org.gam.api.Entities.account.services.changePermissionLevel.IChangePermissionLevelService;
//import br.org.gam.api.Entities.member.IMemberMapper;
//import br.org.gam.api.Entities.member.Member;
//import br.org.gam.api.Entities.member.persistence.IMemberRepository;
//import br.org.gam.api.Entities.member.persistence.MemberEntity;
//import br.org.gam.api.Entities.member.services.getMemberInstance.IGetMemberInstanceService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//import java.util.function.Consumer;
//
//@Service
//public class ActivationService implements IActivationService {
//
//    private final IMemberRepository memberRepo;
//    private final IGetMemberInstanceService getMemberInstanceService;
//    private final IMemberMapper memberMapper;
//
//    public ActivationService(IMemberRepository memberRepo, IGetMemberInstanceService getMemberInstanceService, IMemberMapper memberMapper) {
//        this.memberRepo = memberRepo;
//        this.getMemberInstanceService = getMemberInstanceService;
//        this.memberMapper = memberMapper;
//    }
//
////    @Transactional
////    @Override
////    public void activate(UUID memberId) {
////        changeStatus(memberId, Member::activate, changePermissionLevelService::setToMember);
////    }
////
////    @Transactional
////    @Override
////    public void deactivate(UUID memberId) {
////        changeStatus(memberId, Member::deactivate, changePermissionLevelService::setToVisitor);
////    }
//
//    private void changeStatus(UUID memberId, Consumer<Member> memberConsumer, Consumer<UUID> accountAction) {
//        Member member = getMemberInstanceService.getMemberDomainById(memberId);
//        memberConsumer.accept(member);
//
//        UUID relatedAccountId = member.getAccount().getId();
//        accountAction.accept(relatedAccountId);
//
//        MemberEntity memberEntity = memberMapper.fromDomainToEntity(member);
//        memberRepo.save(memberEntity);
//    }
//
//}
