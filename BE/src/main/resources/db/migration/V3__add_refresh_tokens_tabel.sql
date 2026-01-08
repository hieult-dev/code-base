create table if not exists refresh_tokens (
                                                   id bigserial primary key,
                                                   created_at timestamptz not null,
                                                   expiry_at timestamptz not null,
                                                   token text not null,
                                                   user_id bigint not null
);