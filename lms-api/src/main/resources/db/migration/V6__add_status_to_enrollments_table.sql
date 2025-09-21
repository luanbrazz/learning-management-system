ALTER TABLE enrollments
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED';

COMMENT ON COLUMN enrollments.status IS 'Status do curso para o estudante (NOT_STARTED, IN_PROGRESS, COMPLETED, EXPIRED).';