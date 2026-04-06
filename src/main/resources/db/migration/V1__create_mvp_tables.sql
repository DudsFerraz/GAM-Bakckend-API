CREATE TYPE member_status_enum AS ENUM ('ACTIVE', 'INACTIVE', 'PENDENT');
CREATE TYPE event_status_enum AS ENUM ('SCHEDULED', 'COMPLETED', 'CANCELLED');
CREATE TYPE event_type_enum AS ENUM ('GENERIC', 'MISSA', 'ORATORIO');


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
    WHERE (deleted_at IS NULL);


CREATE TABLE roles(
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,

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
    WHERE (deleted_at IS NULL);


CREATE TABLE permissions(
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,

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
CREATE UNIQUE INDEX idx_permissions_name_not_deleted
    ON permissions (name)
    WHERE (deleted_at IS NULL);


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
    WHERE (deleted_at IS NULL);


CREATE TABLE account_roles(
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    role_id UUID NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_account_role_account FOREIGN KEY(account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT fk_account_role_role FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_account_role_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_account_role_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);
CREATE UNIQUE INDEX idx_account_role_not_deleted
    ON account_roles (account_id, role_id)
    WHERE (deleted_at IS NULL);


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


CREATE TABLE events(
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    location_id UUID,
    required_permission_id UUID,
    type event_type_enum NOT NULL,
    status event_status_enum NOT NULL,

    begin_date TIMESTAMPTZ NOT NULL,
    end_date TIMESTAMPTZ NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT check_event_dates CHECK (end_date > begin_date),
    CONSTRAINT fk_event_location FOREIGN KEY(location_id) REFERENCES locations(id) ON DELETE RESTRICT,
    CONSTRAINT fk_event_permission FOREIGN KEY(required_permission_id) REFERENCES permissions(id) ON DELETE RESTRICT,
    CONSTRAINT fk_event_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_event_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_event_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);


CREATE TABLE members(
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    phone_number VARCHAR(30),
    status member_status_enum NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_member_account FOREIGN KEY(account_id) REFERENCES accounts(id) ON DELETE RESTRICT,
    CONSTRAINT fk_member_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_member_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_member_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);
CREATE UNIQUE INDEX idx_members_account_id_not_deleted
    ON members (account_id)
    WHERE (deleted_at IS NULL);


CREATE TABLE presences(
    id UUID PRIMARY KEY,
    member_id UUID NOT NULL,
    event_id UUID NOT NULL,
    observations TEXT,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_presence_member FOREIGN KEY(member_id) REFERENCES members(id) ON DELETE RESTRICT,
    CONSTRAINT fk_presence_event FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE RESTRICT,
    CONSTRAINT fk_presence_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_presence_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_presence_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);
CREATE UNIQUE INDEX idx_presence_not_deleted
    ON presences (member_id, event_id)
    WHERE (deleted_at IS NULL);


CREATE TABLE oratorios (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL UNIQUE,
    cancellation_reason TEXT,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_oratorio_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_oratorio_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratorio_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratorio_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE TABLE oratorio_lanche(
    member_id UUID NOT NULL,
    oratorio_id UUID NOT NULL,

    PRIMARY KEY (oratorio_id, member_id),
    CONSTRAINT fk_lanche_member FOREIGN KEY(member_id) REFERENCES members(id),
    CONSTRAINT fk_lanche_oratorio FOREIGN KEY(oratorio_id) REFERENCES oratorios(id)
);

CREATE TABLE oratorio_bt_jovens(
    member_id UUID NOT NULL,
    oratorio_id UUID NOT NULL,

    PRIMARY KEY (oratorio_id, member_id),
    CONSTRAINT fk_bt_jovens_member FOREIGN KEY(member_id) REFERENCES members(id),
    CONSTRAINT fk_bt_jovens_oratorio FOREIGN KEY(oratorio_id) REFERENCES oratorios(id)
);

CREATE TABLE oratorio_bt_criancas(
    member_id UUID NOT NULL,
    oratorio_id UUID NOT NULL,

    PRIMARY KEY (oratorio_id, member_id),
    CONSTRAINT fk_bt_criancas_member FOREIGN KEY(member_id) REFERENCES members(id),
    CONSTRAINT fk_bt_criancas_oratorio FOREIGN KEY(oratorio_id) REFERENCES oratorios(id)
);

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

CREATE TABLE oratorio_presences_oratorianos(
    oratoriano_id UUID NOT NULL,
    oratorio_id UUID NOT NULL,

    PRIMARY KEY (oratorio_id, oratoriano_id),
    CONSTRAINT fk_presences_oratoriano FOREIGN KEY(oratoriano_id) REFERENCES oratorianos(id),
    CONSTRAINT fk_presences_oratorio FOREIGN KEY(oratorio_id) REFERENCES oratorios(id)
);

CREATE TABLE missas(
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL UNIQUE,
    comentarios_member UUID,
    leitura_1_member UUID,
    salmo_member UUID,
    leitura_2_member UUID,
    preces_member UUID,

    created_at TIMESTAMPTZ NOT NULL,
    created_by UUID,
    updated_at TIMESTAMPTZ NOT NULL,
    updated_by UUID,
    deleted_at TIMESTAMPTZ,
    deleted_by UUID,

    CONSTRAINT fk_missa_event FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_comentarios_member FOREIGN KEY(comentarios_member) REFERENCES members(id),
    CONSTRAINT fk_leitura_1_member FOREIGN KEY(leitura_1_member) REFERENCES members(id),
    CONSTRAINT fk_salmo_member FOREIGN KEY(salmo_member) REFERENCES members(id),
    CONSTRAINT fk_leitura_2_member FOREIGN KEY(leitura_2_member) REFERENCES members(id),
    CONSTRAINT fk_preces_member FOREIGN KEY(preces_member) REFERENCES members(id),

    CONSTRAINT fk_oratorio_created_by FOREIGN KEY(created_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratorio_updated_by FOREIGN KEY(updated_by) REFERENCES accounts(id),
    CONSTRAINT fk_oratorio_deleted_by FOREIGN KEY(deleted_by) REFERENCES accounts(id)
);

CREATE TABLE missa_acolhida_members(
    member_id UUID,
    missa_id UUID,

    CONSTRAINT fk_acolhida_member FOREIGN KEY(member_id) REFERENCES members(id),
    CONSTRAINT fk_acolhida_missa FOREIGN KEY(missa_id) REFERENCES missas(id)
);