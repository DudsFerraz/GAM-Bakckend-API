CREATE TABLE role_permissions(
    id UUID PRIMARY KEY,
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_role_perm_role FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_perm_permission FOREIGN KEY(permission_id) REFERENCES permissions(id) ON DELETE RESTRICT,
    CONSTRAINT fk_role_perm_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_role_perm_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE UNIQUE INDEX idx_role_perm_not_deleted
    ON role_permissions (role_id, permission_id)
    WHERE deleted_at IS NULL;
