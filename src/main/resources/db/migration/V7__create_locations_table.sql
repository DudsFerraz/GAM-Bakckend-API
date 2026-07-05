CREATE TABLE locations (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    street VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    postal_code VARCHAR(20),
    country_code VARCHAR(3) NOT NULL,
    latitude NUMERIC(10, 8),
    longitude NUMERIC(11, 8),

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_location_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_location_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_location_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);
