ALTER TABLE students
    ADD COLUMN user_id UUID NOT NULL;

ALTER TABLE students
    ADD CONSTRAINT unique_user_student UNIQUE (user_id);

ALTER TABLE students
    ADD CONSTRAINT fk_students_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;

COMMENT
ON COLUMN students.user_id IS 'ID do usuário associado ao estudante.';