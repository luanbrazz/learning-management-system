ALTER TABLE enrollments
    ADD COLUMN expiration_date TIMESTAMP;

ALTER TABLE enrollments
    ALTER COLUMN expiration_date SET NOT NULL;

COMMENT
ON COLUMN enrollments.expiration_date IS 'Data e hora em que o curso expira, 6 meses após a data de matrícula.';