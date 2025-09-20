CREATE TABLE tasks
(
    id                    UUID PRIMARY KEY,
    enrollment_id         UUID NOT NULL,
    task_category_id      UUID NOT NULL,
    description           TEXT NOT NULL,
    log_date              DATE NOT NULL,
    time_spent_in_minutes INT  NOT NULL,
    CONSTRAINT fk_tasks_enrollment
        FOREIGN KEY (enrollment_id)
            REFERENCES enrollments (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_tasks_task_category
        FOREIGN KEY (task_category_id)
            REFERENCES task_categories (id)
            ON DELETE CASCADE
);
COMMENT
ON COLUMN tasks.id IS 'Identificador único da tarefa.';
COMMENT
ON COLUMN tasks.enrollment_id IS 'ID da matrícula à qual a tarefa está associada.';
COMMENT
ON COLUMN tasks.task_category_id IS 'ID da categoria da tarefa.';
COMMENT
ON COLUMN tasks.description IS 'Descrição da tarefa realizada.';
COMMENT
ON COLUMN tasks.log_date IS 'Data de registro da tarefa.';
COMMENT
ON COLUMN tasks.time_spent_in_minutes IS 'Tempo gasto na tarefa, em minutos. Deve ser um múltiplo de 30.';
COMMENT
ON TABLE tasks IS 'Tabela que armazena as tarefas realizadas pelos estudantes em seus cursos.';