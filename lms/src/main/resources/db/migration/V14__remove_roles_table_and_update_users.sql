DO
$$
BEGIN
    IF
EXISTS (
        SELECT 1
          FROM information_schema.table_constraints
         WHERE constraint_name = 'fk_users_role'
           AND table_name = 'users'
    ) THEN
ALTER TABLE users DROP CONSTRAINT fk_users_role;
END IF;
END
$$;

DROP TABLE IF EXISTS roles;

DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1
          FROM information_schema.columns
         WHERE table_name = 'users'
           AND column_name = 'role'
    ) THEN
ALTER TABLE users
    ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'ROLE_STUDENT';
END IF;
END
$$;

DO
$$
BEGIN
    IF
EXISTS (
        SELECT 1
          FROM information_schema.columns
         WHERE table_name = 'users'
           AND column_name = 'role_id'
    ) THEN
ALTER TABLE users DROP COLUMN role_id;
END IF;
END
$$;

UPDATE users
SET role = 'ROLE_ADMIN'
WHERE id = 'a1f18c2d-1a1b-4c3d-8e4f-2d1e3f4a5a5b';

UPDATE users
SET role = 'ROLE_STUDENT'
WHERE role IS NULL;

COMMENT
ON COLUMN users.role IS 'Papel do usuário (ex: ROLE_ADMIN, ROLE_STUDENT).';