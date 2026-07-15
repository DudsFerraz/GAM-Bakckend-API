package br.org.gam.api.member.solicitation.web;

import br.org.gam.api.member.solicitation.application.MembershipSolicitationRDTO;
import br.org.gam.api.member.solicitation.application.useCases.GetMembershipSolicitation;
import br.org.gam.api.member.solicitation.application.useCases.ReviewMembershipSolicitation;
import br.org.gam.api.member.solicitation.application.useCases.ReviewMembershipSolicitationDTO;
import br.org.gam.api.member.solicitation.application.useCases.SearchMembershipSolicitations;
import br.org.gam.api.member.solicitation.application.useCases.SubmitMembershipSolicitation;
import br.org.gam.api.member.solicitation.application.useCases.SubmitMembershipSolicitationDTO;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.shared.specification.SearchDTO;
import br.org.gam.api.shared.web.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/membership-solicitations")
@PreAuthorize("isAuthenticated()")
public class MembershipSolicitationController {
    private final SubmitMembershipSolicitation submit;
    private final GetMembershipSolicitation get;
    private final SearchMembershipSolicitations search;
    private final ReviewMembershipSolicitation review;

    public MembershipSolicitationController(SubmitMembershipSolicitation submit, GetMembershipSolicitation get,
                                            SearchMembershipSolicitations search,
                                            ReviewMembershipSolicitation review) {
        this.submit = submit;
        this.get = get;
        this.search = search;
        this.review = review;
    }

    @Operation(operationId = "submitMembershipSolicitation")
    @PostMapping
    public ResponseEntity<MembershipSolicitationRDTO> submit(
            @RequestBody @Valid SubmitMembershipSolicitationDTO dto) {
        MembershipSolicitationRDTO response = submit.submit(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(operationId = "getMembershipSolicitation")
    @GetMapping("/{id}")
    public ResponseEntity<MembershipSolicitationRDTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(get.byId(id));
    }

    @Operation(operationId = "searchMembershipSolicitations")
    @PostMapping("/search")
    public ResponseEntity<PagedResponse<MembershipSolicitationRDTO>> search(
            @RequestBody @Valid SearchDTO searchDTO, Pageable pageable) {
        return ResponseEntity.ok(PagedResponse.from(search.search(searchDTO, pageable)));
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.MEMBER_MANAGE + "')")
    @Operation(operationId = "approveMembershipSolicitation")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<MembershipSolicitationRDTO> approve(
            @PathVariable UUID id, @RequestBody @Valid ReviewMembershipSolicitationDTO dto) {
        return ResponseEntity.ok(review.approve(id, dto.reason()));
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.MEMBER_MANAGE + "')")
    @Operation(operationId = "rejectMembershipSolicitation")
    @PatchMapping("/{id}/reject")
    public ResponseEntity<MembershipSolicitationRDTO> reject(
            @PathVariable UUID id, @RequestBody @Valid ReviewMembershipSolicitationDTO dto) {
        return ResponseEntity.ok(review.reject(id, dto.reason()));
    }
}
