package br.org.gam.api.Entities.member.security;

import br.org.gam.api.Entities.member.MemberStatus;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import br.org.gam.api.common.auth.AccountDetails;
import br.org.gam.api.common.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Component("memberSecurity")
public class MemberSecurity {
    private final GetMemberInstance getMemberInstance;
    private final SecurityUtils securityUtils;
    public MemberSecurity(GetMemberInstance getMemberInstance, SecurityUtils securityUtils) {
        this.getMemberInstance = getMemberInstance;
        this.securityUtils = securityUtils;
    }

    @Transactional(readOnly = true)
    public boolean canGetMemberPresences(UUID targetMemberId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("PRESENCES_SEARCH"))) return true;

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        UUID loggedAccountId = accountDetails.getId();

        MemberEntity memberEntity = getMemberInstance.entityById(targetMemberId);
        return memberEntity.getAccount().getId().equals(loggedAccountId);
    }

    public boolean canGetMember(MemberEntity memberEntity) {
        if (memberEntity.getStatus() == MemberStatus.ACTIVE) return true;

        Set<String> userAuthorities = securityUtils.getLoggedUserAuthorities();

        return userAuthorities.contains("MEMBER_GET_NON_ACTIVE");
    }
}
