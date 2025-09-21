CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role     VARCHAR(50)         NOT NULL
);

COMMENT
ON COLUMN users.id IS 'Identificador único do usuário.';
COMMENT
ON COLUMN users.username IS 'Nome de usuário (e-mail), deve ser único.';
COMMENT
ON COLUMN users.password IS 'Senha do usuário (hash).';
COMMENT
ON COLUMN users.role IS 'Papel do usuário (ex: ROLE_ADMIN, ROLE_STUDENT).';

COMMENT
ON TABLE users IS 'Tabela que armazena as informações de autenticação de todos os usuários.';