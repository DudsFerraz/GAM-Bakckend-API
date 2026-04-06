package br.org.gam.api.Entities.events.generic.persistence;

import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.events.generic.EventStatus;
import br.org.gam.api.Entities.events.generic.EventType;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.common.auditing.FullAuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter
@Entity
@Getter
@NoArgsConstructor
@Table(name = "events")
public class EventEntity extends FullAuditableEntity {

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "required_permission_id", referencedColumnName = "id")
    private PermissionEntity requiredPermission;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EventType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    @Column(name = "begin_date", nullable = false)
    private Instant beginDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

}
