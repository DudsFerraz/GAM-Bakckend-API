package br.org.gam.api.Entities.oratoriano.persistence;

import br.org.gam.api.common.Name;
import br.org.gam.api.common.auditing.FullAuditableEntity;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "oratorianos")
public class OratorianoEntity extends FullAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Embedded
    private Name name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private MyPhoneNumber phoneNumber;
}
