CREATE TABLE enrollments
(
    id              UUID PRIMARY KEY,
    student_id      UUID NOT NULL,
    course_id       UUID NOT NULL,
    enrollment_date DATE NOT NULL,

    CONSTRAINT unique_enrollment UNIQUE (student_id, course_id),

    CONSTRAINT fk_enrollments_student
        FOREIGN KEY (student_id)
            REFERENCES students (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_enrollments_course
        FOREIGN KEY (course_id)
            REFERENCES courses (id)
            ON DELETE CASCADE
);

COMMENT
ON COLUMN enrollments.id IS 'Identificador único da matrícula.';
COMMENT
ON COLUMN enrollments.student_id IS 'ID do estudante que se matriculou.';
COMMENT
ON COLUMN enrollments.course_id IS 'ID do curso em que o estudante se matriculou.';
COMMENT
ON COLUMN enrollments.enrollment_date IS 'Data em que a matrícula foi realizada.';
COMMENT
ON TABLE enrollments IS 'Tabela que armazena as matrículas de estudantes em cursos.';