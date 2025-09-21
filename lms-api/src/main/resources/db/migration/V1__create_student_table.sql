CREATE TABLE students
(
    id             UUID PRIMARY KEY,
    first_name     VARCHAR(100)        NOT NULL,
    last_name      VARCHAR(100)        NOT NULL,
    birth_date     DATE                NOT NULL,
    email          VARCHAR(255) UNIQUE NOT NULL,
    phone_number   VARCHAR(20),
    zip_code       VARCHAR(10)         NOT NULL,
    address_line_1 VARCHAR(255)        NOT NULL,
    address_line_2 VARCHAR(255),
    city           VARCHAR(100)        NOT NULL,
    state          VARCHAR(100)        NOT NULL
);

COMMENT
ON COLUMN students.id IS 'Identificador único do estudante';
COMMENT
ON COLUMN students.first_name IS 'Primeiro nome do estudante';
COMMENT
ON COLUMN students.last_name IS 'Último nome do estudante';
COMMENT
ON COLUMN students.birth_date IS 'Data de nascimento do estudante, usada para validar a idade mínima';
COMMENT
ON COLUMN students.email IS 'E-mail do estudante, deve ser único';
COMMENT
ON COLUMN students.phone_number IS 'Número de telefone do estudante';
COMMENT
ON COLUMN students.zip_code IS 'CEP do endereço do estudante';
COMMENT
ON COLUMN students.address_line_1 IS 'Primeira linha do endereço (rua, número)';
COMMENT
ON COLUMN students.address_line_2 IS 'Segunda linha do endereço (complemento)';
COMMENT
ON COLUMN students.city IS 'Cidade do endereço do estudante';
COMMENT
ON COLUMN students.state IS 'Estado do endereço do estudante';

COMMENT
ON TABLE students IS 'Tabela que armazena informações sobre os estudantes do sistema LMS.';