CREATE TABLE activity_logs (
    id UUID PRIMARY KEY,
    occurred_at TIMESTAMPTZ NOT NULL,
    actor_account_id UUID,
    action VARCHAR(100) NOT NULL,
    target_type VARCHAR(100) NOT NULL,
    target_id UUID NOT NULL,
    reason TEXT,
    summary TEXT,
    metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
    request_id VARCHAR(100),
    ip_address VARCHAR(100),
    user_agent TEXT,

    CONSTRAINT fk_activity_logs_actor_account
        FOREIGN KEY(actor_account_id) REFERENCES accounts(id)
);

CREATE INDEX idx_activity_logs_occurred_at
    ON activity_logs (occurred_at DESC);

CREATE INDEX idx_activity_logs_target
    ON activity_logs (target_type, target_id);

CREATE INDEX idx_activity_logs_actor_account
    ON activity_logs (actor_account_id);
