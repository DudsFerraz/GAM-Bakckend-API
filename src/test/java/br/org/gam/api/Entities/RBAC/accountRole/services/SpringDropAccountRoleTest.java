package br.org.gam.api.Entities.RBAC.accountRole.services;

import br.org.gam.api.Entities.RBAC.accountRole.exception.AccountRoleNotFoundException;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.Entities.RBAC.accountRole.services.dropAccountRole.SpringDropAccountRole;
import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoleInstance.GetAccountRoleInstance;
import br.org.gam.api.Entities.RBAC.role.exception.RoleNotFoundException;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleInstance.GetRoleInstance;
import br.org.gam.api.testing.annotation.FunctionalTest;
import br.org.gam.api.testing.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@UnitTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Drop Account Role Use Case")
class SpringDropAccountRoleTest {

    @Mock
    private GetAccountRoleInstance getAccountRoleInstance;

    @Mock
    private AccountRoleRepository accountRoleRepo;

    @Mock
    private GetRoleInstance getRoleInstance;

    @InjectMocks
    private SpringDropAccountRole dropAccountRole;

    @Nested
    @FunctionalTest
    @DisplayName("Functional")
    class Functional {

        @Test
        @DisplayName("EP - existing account role -> account role is deleted")
        void existingAccountRoleShouldDeleteAccountRole() {
            AccountRoleDTO dto = new AccountRoleDTO(UUID.randomUUID(), UUID.randomUUID());
            AccountRoleEntity entity = new AccountRoleEntity();

            when(getAccountRoleInstance.entityByDTO(dto)).thenReturn(entity);

            dropAccountRole.byDTO(dto);

            verify(accountRoleRepo).delete(entity);
        }

        @Test
        @DisplayName("EP - missing account role -> not found error")
        void missingAccountRoleShouldReturnNotFoundError() {
            AccountRoleDTO dto = new AccountRoleDTO(UUID.randomUUID(), UUID.randomUUID());

            String message = "Account with id: " + dto.accountId() + " does not have role with id: " + dto.roleId();
            when(getAccountRoleInstance.entityByDTO(dto))
                    .thenThrow(new AccountRoleNotFoundException(message));

            assertThatThrownBy(() -> dropAccountRole.byDTO(dto))
                    .isInstanceOf(AccountRoleNotFoundException.class)
                    .hasMessage(message);

            verify(accountRoleRepo, never()).delete(org.mockito.ArgumentMatchers.any());
        }

        @Test
        @DisplayName("EP - role name and account id -> account role is deleted")
        void roleNameAndAccountIdShouldDeleteAccountRole() {
            UUID accountId = UUID.randomUUID();
            UUID roleId = UUID.randomUUID();
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(roleId);
            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();

            when(getRoleInstance.entityByName("ADMIN")).thenReturn(roleEntity);
            when(getAccountRoleInstance.entityByDTO(new AccountRoleDTO(accountId, roleId))).thenReturn(accountRoleEntity);

            dropAccountRole.byRoleName("ADMIN", accountId);

            verify(getRoleInstance).entityByName("ADMIN");
            verify(accountRoleRepo).delete(accountRoleEntity);
        }

        @Test
        @DisplayName("EP - missing role name -> not found error")
        void missingRoleNameShouldReturnNotFoundError() {
            UUID accountId = UUID.randomUUID();

            when(getRoleInstance.entityByName("ADMIN"))
                    .thenThrow(new RoleNotFoundException("Could not find role with name ADMIN"));

            assertThatThrownBy(() -> dropAccountRole.byRoleName("ADMIN", accountId))
                    .isInstanceOf(RoleNotFoundException.class)
                    .hasMessage("Could not find role with name ADMIN");

            verifyNoInteractions(getAccountRoleInstance);
            verify(accountRoleRepo, never()).delete(org.mockito.ArgumentMatchers.any());
        }
    }
}
