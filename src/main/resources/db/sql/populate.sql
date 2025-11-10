--====================================================================
-- 1. LIMPEZA DOS DADOS (ORDEM REVERSA DE DEPENDÊNCIA)
-- Isso permite que o script seja executado várias vezes sem erro.
--====================================================================
DELETE FROM presences;
DELETE FROM events;
DELETE FROM locations;
DELETE FROM members;
DELETE FROM accounts;

--====================================================================
-- 2. ACCOUNTS
-- Criando 3 contas: Coordenador, Membro e Visitante.
-- O hash da senha é um placeholder (ex: "senha123" em bcrypt)
--====================================================================
INSERT INTO accounts (id, email, password_hash, display_name, permission_level)
VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ana.coord@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Ana Coordenadora', 'COORDINATOR'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'bruno.membro@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Bruno Membro', 'MEMBER'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'carla.visitante@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Carla Visitante', 'VISITOR'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'daniel.pendente@example.com', '$2a$10$3b.y.QGg.N/1.31y1D0Vn.9g4WkId7E..mC1wZJ.5D.dZ3.S/2qjS', 'Daniel Pendente', 'MEMBER');

--====================================================================
-- 3. LOCATIONS
-- Criando 2 locais para os eventos.
--====================================================================
INSERT INTO locations (id, name, street, city, state, postal_code, country_code, latitude, longitude)
VALUES
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Sede Principal GAM', 'Rua Fictícia, 123', 'São Paulo', 'SP', '01000-000', 'BR', -23.550520, -46.633300),
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Salão de Eventos Anexo', 'Avenida Brasil, 456', 'Rio de Janeiro', 'RJ', '20000-000', 'BR', -22.906847, -43.172896);

--====================================================================
-- 4. MEMBERS
-- Criando os perfis de membro, ligados às contas.
--====================================================================
INSERT INTO members (id, account_id, first_name, surname, birth_date, phone_number, status)
VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Ana', 'Souza', '1990-05-15', '+5511987654321', 'ACTIVE'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Bruno', 'Silva', '2000-02-20', '+5521912345678', 'ACTIVE'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Carla', 'Mendes', '1998-11-10', '+5511924681357', 'INACTIVE'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Daniel', 'Gomes', '2002-07-30', '+5519988776655', 'PENDENT');

--====================================================================
-- 5. EVENTS
-- Criando 4 eventos: 1 para Coords, 1 para Membros, 1 para Visitantes, 1 no passado.
--====================================================================
INSERT INTO events (id, title, description, location_id, required_permission_level, begin_date, end_date)
VALUES
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Reunião de Coordenadores', 'Planejamento estratégico do próximo mês.', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'COORDINATOR', NOW() + interval '2 day', NOW() + interval '2 day 2 hour'),
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Encontro Semanal GAM', 'Encontro geral para todos os membros ativos.', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'MEMBER', NOW() + interval '5 day', NOW() + interval '5 day 3 hour'),
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Evento "Portas Abertas"', 'Evento para visitantes conhecerem o projeto. Não requer local fixo.', null, 'VISITOR', NOW() + interval '10 day', NOW() + interval '10 day 4 hour'),
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Palestra sobre Voluntariado (Passado)', 'Registro de evento que já ocorreu.', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'MEMBER', NOW() - interval '7 day', NOW() - interval '7 day 2 hour');

--====================================================================
-- 6. PRESENCES
-- Registrando quem foi em quê.
--====================================================================
INSERT INTO presences (member_id, event_id, observations)
VALUES
-- Ana (Coord) na reunião de Coordenadores
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Liderou a reunião.'),
-- Ana (Coord) e Bruno (Membro) no evento passado
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', null),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Fez uma pergunta sobre logística.');