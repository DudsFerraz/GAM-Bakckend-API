create extension if not exists "uuid-ossp";


create type permission_level_enum as enum ('COORDINATOR', 'MEMBER');
create type member_status_enum as enum ('ACTIVE', 'INACTIVE', 'PENDENT');


CREATE OR REPLACE FUNCTION trigger_set_updated_at()
RETURNS TRIGGER AS $$
    BEGIN
        NEW.updated_at = NOW();
    RETURN NEW;
    END;
$$ LANGUAGE plpgsql;



create table accounts(
    id UUID primary key,
    email varchar(255) unique not null,
    password text not null,
    permission_level permission_level_enum default 'MEMBER',

    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create trigger set_accounts_updated_at
    before update on accounts
    for each row
    execute procedure trigger_set_updated_at();



create table members(
    id UUID primary key,
    account_id UUID unique not null,
    name varchar(255) not null,
    birth_date date not null,
    phone_number varchar(30),
    status member_status_enum default 'PENDENT',

    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),

    constraint fk_member_account foreign key(account_id) references accounts(id) on delete restrict
);
create trigger set_members_updated_at
    before update on members
    for each row
    execute procedure trigger_set_updated_at();



create table events(
    id UUID primary key,
    title varchar(255) not null,
    description text,
    location varchar(255) not null,
    begin_date timestamptz not null,
    end_date timestamptz not null,

    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);
create trigger set_events_updated_at
    before update on events
    for each row
    execute procedure trigger_set_updated_at();



create table presences(
    member_id UUID,
    event_id UUID,

    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),

    primary key (member_id, event_id),
    constraint fk_presence_member foreign key(member_id) references members(id) on delete restrict,
    constraint fk_presence_event foreign key(event_id) references events(id) on delete restrict
);
create trigger set_presences_updated_at
    before update on presences
    for each row
    execute procedure trigger_set_updated_at();