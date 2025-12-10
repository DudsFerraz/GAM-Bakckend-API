package br.org.gam.api.Entities.member.persistence;

import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.member.MemberStatus;
import br.org.gam.api.common.Name;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;
import br.org.gam.api.common.persistence.FullAuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "members")
public class MemberEntity extends FullAuditableEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, unique = true)
    private AccountEntity account;

    @Embedded
    private Name name;

    @Formula("first_name || ' ' || surname")
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private MyPhoneNumber phoneNumber;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private MemberStatus status;
}
