ALTER TABLE enrollments
    ADD COLUMN completion_date TIMESTAMP;

ALTER TABLE enrollments
ALTER
COLUMN enrollment_date TYPE TIMESTAMP;

COMMENT
ON COLUMN enrollments.enrollment_date IS 'Data e hora em que a matrícula foi realizada.';
COMMENT
ON COLUMN enrollments.completion_date IS 'Data e hora em que a matrícula foi finalizada (concluída ou expirada).';