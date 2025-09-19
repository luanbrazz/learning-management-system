CREATE TABLE courses
(
    id                 UUID PRIMARY KEY,
    name               VARCHAR(255) UNIQUE NOT NULL,
    description        TEXT,
    duration_in_months INT                 NOT NULL
);
COMMENT
ON COLUMN courses.id IS 'Identificador único do curso.';
COMMENT
ON COLUMN courses.name IS 'Nome do curso. Deve ser único.';
COMMENT
ON COLUMN courses.description IS 'Descrição do conteúdo do curso.';
COMMENT
ON COLUMN courses.duration_in_months IS 'Duração máxima do curso em meses (limitado a 6).';
COMMENT
ON TABLE courses IS 'Tabela que armazena informações sobre os cursos do sistema LMS.';