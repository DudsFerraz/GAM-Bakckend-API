package br.org.gam.api.Entities.member.controller;

import br.org.gam.api.Entities.member.services.getMemberById.GetMemberByIdDTO;
import br.org.gam.api.Entities.member.services.getMemberById.GetMemberById;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMemberRDTO;
import br.org.gam.api.Entities.member.services.registerMember.RegisterMember;
import br.org.gam.api.Entities.member.services.searchMembers.SearchMembers;
import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
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

    private final RegisterMember registerMemberService;
    private final GetMemberById getMemberByIdService;
    private final SpecificationFilterConverter specificationFilterConverter;
    private final SearchMembers searchMembersService;
    private final GetPresencesByMember getPresencesByMemberService;

    public MemberController(RegisterMember registerMemberService,
                            GetMemberById getMemberByIdService,
                            @Qualifier("memberSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter,
                            SearchMembers searchMembersService,
                            GetPresencesByMember getPresencesByMemberService) {

        this.registerMemberService = registerMemberService;
        this.getMemberByIdService = getMemberByIdService;
        this.specificationFilterConverter = specificationFilterConverter;
        this.searchMembersService = searchMembersService;
        this.getPresencesByMemberService = getPresencesByMemberService;
    }

    @PostMapping
    public ResponseEntity<RegisterMemberRDTO> registerMember(@RequestBody @Valid RegisterMemberDTO dto) {

        RegisterMemberRDTO responseDTO = registerMemberService.registerMember(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMemberByIdDTO> getMemberById(@PathVariable UUID id) {
        GetMemberByIdDTO dto = getMemberByIdService.getMemberById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetMemberByIdDTO>> searchMembers(@RequestBody @Valid SearchDTO searchDTO,
                                                                Pageable pageable) {

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchMembersService.searchMembers(filters, pageable)
        );
    }

//    @PatchMapping("/{id}/activate")
//    public ResponseEntity activate(@PathVariable UUID id) {
//
//        activationService.activate(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{id}/deactivate")
//    public ResponseEntity deactivate(@PathVariable UUID id) {
//
//        activationService.deactivate(id);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{memberId}/presences")
    public ResponseEntity<Page<GetPresenceByIdDTO>> getMemberPresences(@PathVariable UUID memberId,
                                                                       Pageable pageable) {

        return ResponseEntity.ok(
                getPresencesByMemberService.getMemberPresences(memberId, pageable)
        );
    }
}
