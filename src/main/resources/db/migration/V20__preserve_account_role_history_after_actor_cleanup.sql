ALTER TABLE account_roles
    DROP CONSTRAINT fk_account_role_created_by,
    DROP CONSTRAINT fk_account_role_deleted_by;

ALTER TABLE account_roles
    ADD CONSTRAINT fk_account_role_created_by
        FOREIGN KEY (created_by) REFERENCES accounts(id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_account_role_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES accounts(id) ON DELETE SET NULL;
