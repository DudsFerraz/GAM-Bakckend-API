package br.org.gam.api.Entities.account.persistence;

import br.org.gam.api.Entities.account.MyEmail;
import br.org.gam.api.common.persistence.FullAuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import java.util.UUID;

@SQLRestriction("deleted_at is NULL")
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity extends FullAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Convert(converter = EmailConverterJPA.class)
    @Column(name = "email", nullable = false)
    private MyEmail email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "display_name", nullable = false)
    private String displayName;
}
