-- noinspection SqlResolveForFile @ routine/"uuidv7"

DO $$
    DECLARE
        v_role_sudo_id UUID := uuidv7();
        v_role_coord_id UUID := uuidv7();
        v_role_member_id UUID := uuidv7();
        v_role_visitor_id UUID := uuidv7();

        v_perm_id UUID;

        v_now TIMESTAMPTZ := NOW();
    BEGIN

        INSERT INTO roles (id, name, description, created_at, updated_at)
            VALUES
            (v_role_sudo_id, 'SUDO', 'Super Usuário com acesso total ao sistema', v_now, v_now),
            (v_role_coord_id, 'COORD', 'Coordenador: Gestão de eventos, membros e locais', v_now, v_now),
            (v_role_member_id, 'MEMBER', 'Membro: Acesso a visualização e inscrição', v_now, v_now),
            (v_role_visitor_id, 'VISITOR', 'Visitante: Acesso público limitado', v_now, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'event:create', 'Criar novos eventos', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_coord_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'event:update', 'Editar eventos existentes', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_coord_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'event:delete', 'Excluir eventos', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_coord_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'event:view', 'Visualizar detalhes de eventos restritos', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_member_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'event:subscribe', 'Inscrever-se em eventos', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_member_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'member:manage_status', 'Ativar ou inativar membros', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_coord_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

        v_perm_id := uuidv7();
        INSERT INTO permissions (id, name, description, created_at, updated_at)
            VALUES (v_perm_id, 'location:manage', 'Criar, editar e excluir locais', v_now, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_coord_id, v_perm_id, v_now);
        INSERT INTO role_permissions (id, role_id, permission_id, created_at)
            VALUES (uuidv7(), v_role_sudo_id, v_perm_id, v_now);

    END $$;