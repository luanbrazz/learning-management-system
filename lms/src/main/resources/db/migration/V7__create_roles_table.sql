CREATE TABLE roles
(
    id   UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
COMMENT
ON COLUMN roles.id IS 'Identificador único da role.';
COMMENT
ON COLUMN roles.name IS 'Nome da role (ex: ROLE_ADMIN, ROLE_STUDENT).';
COMMENT
ON TABLE roles IS 'Tabela que armazena os diferentes papeis de usuário no sistema.';