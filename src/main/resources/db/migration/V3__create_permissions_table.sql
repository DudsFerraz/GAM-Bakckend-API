CREATE TABLE permissions(
    id UUID PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    label VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    system_managed BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_permissions_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_permissions_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_permissions_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE UNIQUE INDEX idx_permissions_code_not_deleted
    ON permissions (code)
    WHERE deleted_at IS NULL;
