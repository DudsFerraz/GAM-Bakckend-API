package br.org.gam.api.Entities.member.controller;

import br.org.gam.api.Entities.member.services.activation.Activation;
import br.org.gam.api.Entities.member.services.getMember.GetMemberRDTO;
import br.org.gam.api.Entities.member.services.getMember.GetMember;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberRDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMember;
import br.org.gam.api.Entities.member.services.searchMembers.SearchMembers;
import br.org.gam.api.Entities.presence.services.getPresence.GetPresenceRDTO;
import br.org.gam.api.Entities.presence.services.getPresencesByMember.GetPresencesByMember;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final GetPresencesByMember getPresencesByMember;
    private final Activation activation;

    public MemberController(RegisterMember registerMember,
                            GetMember getMember,
                            @Qualifier("memberSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter,
                            SearchMembers searchMembers,
                            GetPresencesByMember getPresencesByMember, Activation activation) {

        this.registerMember = registerMember;
        this.getMember = getMember;
        this.specificationFilterConverter = specificationFilterConverter;
        this.searchMembers = searchMembers;
        this.getPresencesByMember = getPresencesByMember;
        this.activation = activation;
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

    @GetMapping("/{id}")
    public ResponseEntity<GetMemberRDTO> getMemberById(@PathVariable UUID id) {
        GetMemberRDTO dto = getMember.byId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetMemberRDTO>> searchMembers(@RequestBody @Valid SearchDTO searchDTO,
                                                             Pageable pageable) {

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchMembers.search(filters, pageable)
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity activate(@PathVariable UUID id) {

        activation.activate(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity deactivate(@PathVariable UUID id) {

        activation.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}/presences")
    public ResponseEntity<Page<GetPresenceRDTO>> getMemberPresences(@PathVariable UUID memberId,
                                                                    Pageable pageable) {

        return ResponseEntity.ok(
                getPresencesByMember.getMemberPresences(memberId, pageable)
        );
    }
}
