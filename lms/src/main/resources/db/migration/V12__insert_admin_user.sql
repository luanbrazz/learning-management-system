INSERT INTO users (id, username, password, role_id)
VALUES ('a1f18c2d-1a1b-4c3d-8e4f-2d1e3f4a5a5b',
        'admin@email.com',
        '$2a$12$cO7oLalu2NS8pAGo5MKn3eyZsXNXxbCEXtV6TapRI1J2Eoq/6ksMW',
        (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));