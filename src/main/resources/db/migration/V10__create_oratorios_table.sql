CREATE TABLE oratorios (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL,
    cancellation_reason TEXT,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_oratorio_event FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_oratorio_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratorio_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratorio_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE UNIQUE INDEX idx_oratorios_event_id_not_deleted
    ON oratorios (event_id)
    WHERE deleted_at IS NULL;
