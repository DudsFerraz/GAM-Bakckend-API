-- noinspection SqlResolveForFile @ routine/"uuidv7"

DO $$
    DECLARE
        v_role_sudo_id UUID;
        v_role_coord_id UUID;
        v_role_member_id UUID;
        v_role_visitor_id UUID;

        v_acc_ana_id UUID;
        v_acc_bruno_id UUID;
        v_acc_carla_id UUID;
        v_acc_daniel_id UUID;

        v_loc_sede_id UUID;
        v_loc_anexo_id UUID;

        v_member_ana_id UUID;
        v_member_bruno_id UUID;
        v_member_daniel_id UUID;

        v_event_reuniao_id UUID;
        v_event_encontro_id UUID;
        v_event_palestra_id UUID;

        v_perm_manage_loc_id UUID;
        v_perm_event_view_id UUID;

        v_now TIMESTAMPTZ := NOW();

    BEGIN

        SELECT id INTO v_role_sudo_id FROM roles WHERE name = 'SUDO';
        SELECT id INTO v_role_coord_id FROM roles WHERE name = 'COORD';
        SELECT id INTO v_role_member_id FROM roles WHERE name = 'MEMBER';
        SELECT id INTO v_role_visitor_id FROM roles WHERE name = 'VISITOR';

        SELECT id INTO v_perm_event_view_id FROM permissions WHERE name = 'EVENT_CREATE';
        SELECT id INTO v_perm_manage_loc_id FROM permissions WHERE name = 'LOCATION_MANAGE';

        SELECT id INTO v_acc_ana_id FROM accounts WHERE email = 'ana.coord@example.com';
        IF v_acc_ana_id IS NULL THEN
            v_acc_ana_id := uuidv7();
            INSERT INTO accounts (id, email, password_hash, display_name, created_at, updated_at)
                VALUES (v_acc_ana_id, 'ana.coord@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Ana Coordenadora', v_now, v_now);

            INSERT INTO account_roles (id, account_id, role_id, created_at)
                VALUES (uuidv7(), v_acc_ana_id, v_role_member_id, v_now);
            INSERT INTO account_roles (id, account_id, role_id, created_at)
                VALUES (uuidv7(), v_acc_ana_id, v_role_coord_id, v_now);
        END IF;

        SELECT id INTO v_acc_bruno_id FROM accounts WHERE email = 'bruno.membro@example.com';
        IF v_acc_bruno_id IS NULL THEN
            v_acc_bruno_id := uuidv7();
            INSERT INTO accounts (id, email, password_hash, display_name, created_at, updated_at)
                VALUES (v_acc_bruno_id, 'bruno.membro@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Bruno Membro', v_now, v_now);

            INSERT INTO account_roles (id, account_id, role_id, created_at)
                VALUES (uuidv7(), v_acc_bruno_id, v_role_member_id, v_now);
        END IF;

        SELECT id INTO v_acc_carla_id FROM accounts WHERE email = 'carla.visitante@example.com';
        IF v_acc_carla_id IS NULL THEN
            v_acc_carla_id := uuidv7();
            INSERT INTO accounts (id, email, password_hash, display_name, created_at, updated_at)
                VALUES (v_acc_carla_id, 'carla.visitante@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Carla Visitante', v_now, v_now);

            INSERT INTO account_roles (id, account_id, role_id, created_at)
                VALUES (uuidv7(), v_acc_carla_id, v_role_visitor_id, v_now);
        END IF;

        SELECT id INTO v_acc_daniel_id FROM accounts WHERE email = 'daniel.pendente@example.com';
        IF v_acc_daniel_id IS NULL THEN
            v_acc_daniel_id := uuidv7();
            INSERT INTO accounts (id, email, password_hash, display_name, created_at, updated_at)
                VALUES (v_acc_daniel_id, 'daniel.pendente@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Daniel Pendente', v_now, v_now);

            INSERT INTO account_roles (id, account_id, role_id, created_at)
                VALUES (uuidv7(), v_acc_daniel_id, v_role_member_id, v_now);
        END IF;

        SELECT id INTO v_loc_sede_id FROM locations WHERE name = 'Sede Principal GAM';
        IF v_loc_sede_id IS NULL THEN
            v_loc_sede_id := uuidv7();
            INSERT INTO locations (id, name, street, city, state, postal_code, country_code, latitude, longitude, created_at, updated_at, created_by)
                VALUES (v_loc_sede_id, 'Sede Principal GAM', 'Rua Fictícia, 123', 'São Paulo', 'SP', '01000-000', 'BR', -23.550520, -46.633300, v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_loc_anexo_id FROM locations WHERE name = 'Salão de Eventos Anexo';
        IF v_loc_anexo_id IS NULL THEN
            v_loc_anexo_id := uuidv7();
            INSERT INTO locations (id, name, street, city, state, postal_code, country_code, latitude, longitude, created_at, updated_at, created_by)
                VALUES (v_loc_anexo_id, 'Salão de Eventos Anexo', 'Avenida Brasil, 456', 'Rio de Janeiro', 'RJ', '20000-000', 'BR', -22.906847, -43.172896, v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_member_ana_id FROM members WHERE account_id = v_acc_ana_id;
        IF v_member_ana_id IS NULL THEN
            v_member_ana_id := uuidv7();
            INSERT INTO members (id, account_id, first_name, surname, birth_date, phone_number, status, created_at, updated_at, created_by)
                VALUES (v_member_ana_id, v_acc_ana_id, 'Ana', 'Souza', '1990-05-15', '+5511987654321', 'ACTIVE', v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_member_bruno_id FROM members WHERE account_id = v_acc_bruno_id;
        IF v_member_bruno_id IS NULL THEN
            v_member_bruno_id := uuidv7();
            INSERT INTO members (id, account_id, first_name, surname, birth_date, phone_number, status, created_at, updated_at, created_by)
                VALUES (v_member_bruno_id, v_acc_bruno_id, 'Bruno', 'Silva', '2000-02-20', '+5521912345678', 'ACTIVE', v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_member_daniel_id FROM members WHERE account_id = v_acc_daniel_id;
        IF v_member_daniel_id IS NULL THEN
            v_member_daniel_id := uuidv7();
            INSERT INTO members (id, account_id, first_name, surname, birth_date, phone_number, status, created_at, updated_at, created_by)
                VALUES (v_member_daniel_id, v_acc_daniel_id, 'Daniel', 'Gomes', '2002-07-30', '+5519988776655', 'PENDENT', v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_event_reuniao_id FROM events WHERE title = 'Reunião de Coordenadores';
        IF v_event_reuniao_id IS NULL THEN
            v_event_reuniao_id := uuidv7();
            INSERT INTO events (id, title, description, location_id, required_permission_id, begin_date, end_date, created_at, updated_at, created_by)
                VALUES (v_event_reuniao_id, 'Reunião de Coordenadores', 'Planejamento estratégico.', v_loc_sede_id, v_perm_manage_loc_id, v_now + interval '2 day', v_now + interval '2 day 2 hour', v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_event_encontro_id FROM events WHERE title = 'Encontro Semanal GAM';
        IF v_event_encontro_id IS NULL THEN
            v_event_encontro_id := uuidv7();
            INSERT INTO events (id, title, description, location_id, required_permission_id, begin_date, end_date, created_at, updated_at, created_by)
                VALUES (v_event_encontro_id, 'Encontro Semanal GAM', 'Encontro geral.', v_loc_anexo_id, v_perm_event_view_id, v_now + interval '5 day', v_now + interval '5 day 3 hour', v_now, v_now, v_acc_ana_id);
        END IF;

        SELECT id INTO v_event_palestra_id FROM events WHERE title = 'Palestra sobre Voluntariado (Passado)';
        IF v_event_palestra_id IS NULL THEN
            v_event_palestra_id := uuidv7();
            INSERT INTO events (id, title, description, location_id, required_permission_id, begin_date, end_date, created_at, updated_at, created_by)
                VALUES (v_event_palestra_id, 'Palestra sobre Voluntariado (Passado)', 'Evento já ocorreu.', v_loc_sede_id, v_perm_event_view_id, v_now - interval '7 day', v_now - interval '6 day 22 hour', v_now, v_now, v_acc_ana_id);
        END IF;

        IF NOT EXISTS (SELECT 1 FROM events WHERE title = 'Evento Portas Abertas') THEN
            INSERT INTO events (id, title, description, location_id, required_permission_id, begin_date, end_date, created_at, updated_at, created_by)
                VALUES (uuidv7(), 'Evento Portas Abertas', 'Aberto ao público.', NULL, NULL, v_now + interval '10 day', v_now + interval '10 day 4 hour', v_now, v_now, v_acc_ana_id);
        END IF;

        IF NOT EXISTS (SELECT 1 FROM presences WHERE member_id = v_member_ana_id AND event_id = v_event_reuniao_id) THEN
            INSERT INTO presences (id, member_id, event_id, observations, created_at, updated_at, created_by)
                VALUES (uuidv7(), v_member_ana_id, v_event_reuniao_id, 'Liderou a pauta', v_now, v_now, v_acc_ana_id);
        END IF;

        IF NOT EXISTS (SELECT 1 FROM presences WHERE member_id = v_member_ana_id AND event_id = v_event_palestra_id) THEN
            INSERT INTO presences (id, member_id, event_id, observations, created_at, updated_at, created_by)
            VALUES (uuidv7(), v_member_ana_id, v_event_palestra_id, NULL, v_now, v_now, v_acc_ana_id);
        END IF;

        IF NOT EXISTS (SELECT 1 FROM presences WHERE member_id = v_member_bruno_id AND event_id = v_event_palestra_id) THEN
            INSERT INTO presences (id, member_id, event_id, observations, created_at, updated_at, created_by)
                VALUES (uuidv7(), v_member_bruno_id, v_event_palestra_id, 'Chegou atrasado', v_now, v_now, v_acc_ana_id);
        END IF;

    END $$;