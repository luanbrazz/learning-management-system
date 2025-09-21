CREATE TABLE task_categories
(
    id   UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);
COMMENT
ON COLUMN task_categories.id IS 'Identificador único da categoria de tarefa.';
COMMENT
ON COLUMN task_categories.name IS 'Nome da categoria (PESQUISA, PRÁTICA, ASSISTIR_VIDEOAULA).';
COMMENT
ON TABLE task_categories IS 'Tabela que armazena as categorias de tarefas para os cursos.';