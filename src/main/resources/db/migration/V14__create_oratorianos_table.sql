CREATE TABLE oratorianos(
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birth_date DATE,
    phone_number VARCHAR(30),

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_oratoriano_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratoriano_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratoriano_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);
