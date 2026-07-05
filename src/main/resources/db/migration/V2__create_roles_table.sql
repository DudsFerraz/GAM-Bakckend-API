CREATE TABLE roles(
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    system_managed BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_roles_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_roles_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_roles_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE UNIQUE INDEX idx_roles_name_not_deleted
    ON roles (name)
    WHERE deleted_at IS NULL;
