package br.org.gam.api.Entities.event.persistence;

import br.org.gam.api.Entities.location.persistence.LocationEntity;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Setter
@Entity
@Getter
@NoArgsConstructor
@Table(name = "events")
public class EventEntity {

    private static final TimeBasedEpochGenerator uuidV7Generator = Generators.timeBasedEpochGenerator();

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
//    @Column(name = "required_permission_level")
//    private PermissionLevelEnum requiredPermissionLevel;

    @Column(name = "begin_date", nullable = false)
    private Instant beginDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = uuidV7Generator.generate();
        }
    }
}
