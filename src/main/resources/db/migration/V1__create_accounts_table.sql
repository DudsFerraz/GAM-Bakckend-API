CREATE TABLE accounts(
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password_hash TEXT NOT NULL,
    display_name VARCHAR(50) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_account_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_account_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_account_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE UNIQUE INDEX idx_accounts_email_not_deleted
    ON accounts (email)
    WHERE deleted_at IS NULL;
