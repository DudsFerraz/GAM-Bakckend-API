package br.org.gam.api.Entities.member.controller;

import br.org.gam.api.Entities.RBAC.permission.PermissionEnum;
import br.org.gam.api.Entities.member.services.activation.Activation;
import br.org.gam.api.Entities.member.services.MemberRDTO;
import br.org.gam.api.Entities.member.services.getMember.GetMember;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberRDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMember;
import br.org.gam.api.Entities.member.services.searchMembers.SearchMembers;
import br.org.gam.api.Entities.presence.services.getPresence.GetPresence;
import br.org.gam.api.Entities.presence.services.PresenceRDTO;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final RegisterMember registerMember;
    private final GetMember getMember;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final SearchMembers searchMembers;
    private final Activation activation;
    private final GetPresence getPresence;

    public MemberController(RegisterMember registerMember, GetMember getMember, SearchMembers searchMembers,
                            @Qualifier("memberSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter,
                            Activation activation, GetPresence getPresence
    ) {

        this.registerMember = registerMember;
        this.getMember = getMember;
        this.specificationFilterConverter = specificationFilterConverter;
        this.searchMembers = searchMembers;
        this.activation = activation;
        this.getPresence = getPresence;
    }

    @PostMapping
    public ResponseEntity<RegisterMemberRDTO> registerMember(@RequestBody @Valid RegisterMemberDTO dto) {

        RegisterMemberRDTO responseDTO = registerMember.register(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.MEMBER_GET + "')")
    @GetMapping("/{id}")
    public ResponseEntity<MemberRDTO> getMemberById(@PathVariable UUID id) {
        MemberRDTO dto = getMember.byId(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.MEMBER_SEARCH + "')")
    @PostMapping("/search")
    public ResponseEntity<Page<MemberRDTO>> searchMembers(@RequestBody @Valid SearchDTO searchDTO,
                                                          Pageable pageable) {

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchMembers.search(filters, pageable)
        );
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.MEMBER_ACTIVATION + "')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity activate(@PathVariable UUID id) {

        activation.activate(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.MEMBER_ACTIVATION + "')")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity deactivate(@PathVariable UUID id) {

        activation.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@memberSecurity.canGetMemberPresences(#memberId)")
    @GetMapping("/{memberId}/presences")
    public ResponseEntity<Page<PresenceRDTO>> getMemberPresences(@PathVariable UUID memberId,
                                                                 Pageable pageable) {

        return ResponseEntity.ok(
                getPresence.allByMember(memberId, pageable)
        );
    }
}
