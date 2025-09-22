-- Inserção de um usuário administrador - senha: senha123
INSERT INTO users (id, username, password, role)
VALUES ('49266943-4ee6-40ff-83f1-a9d618eda42b', 'admin@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_ADMIN');

-- Inserção de usuários e estudantes fictícios - senhas: senha123
INSERT INTO users (id, username, password, role)
VALUES ('0095676f-aeb4-4fd9-9be2-68f5ef928eee', 'joao.silva@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('ffe61b73-512a-45c5-ac23-8ddf646afe39', 'maria.souza@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('620d29d5-9bfe-49c9-b469-e8c2f05ea3ee', 'carlos.almeida@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('b6952301-1426-4cca-9b21-72a30d0f9656', 'ana.paula@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('7f40510e-d073-4ab4-a99f-4a8051d8005d', 'fernanda.lima@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('be021e76-e00a-48fe-b1d7-00cbe881ca89', 'pedro.costa@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('2f995ec4-9e45-41b2-b689-8befdd0122d5', 'camila.santos@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('06bc7a27-ad04-4a64-81cd-426a0c8b17f4', 'bruno.pereira@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('fb8d364f-aa62-4aef-8fa5-dfc74b882c0e', 'lara.ribeiro@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT'),
       ('8ea3678c-4731-4f12-825d-782950a57aff', 'gabriel.olivera@lms.com',
        '$2a$10$3TyjtPBs.5udQlRa9b3h/uTR/adppNUOcoYBrLt34UFv9UZA0l7K6', 'ROLE_STUDENT');

INSERT INTO students (id, user_id, first_name, last_name, birth_date, email, phone_number, zip_code, address_line_1,
                      city, state)
VALUES ('c0bda8af-3d05-4071-9ce2-d1b1ca27981a', '0095676f-aeb4-4fd9-9be2-68f5ef928eee', 'João', 'Silva', '2000-01-01',
        'joao.silva@lms.com', '11987654321', '12345678', 'Rua da Programação, 123', 'Caçapava', 'SP'),
       ('f9669c1e-b9df-454e-8c7c-0837f8033a48', 'ffe61b73-512a-45c5-ac23-8ddf646afe39', 'Maria', 'Souza', '1998-05-15',
        'maria.souza@lms.com', '11998765432', '12345678', 'Avenida Principal, 456', 'São Paulo', 'SP'),
       ('d6146d0c-c281-4e6d-b25f-2147a8cd0520', '620d29d5-9bfe-49c9-b469-e8c2f05ea3ee', 'Carlos', 'Almeida',
        '1995-10-20', 'carlos.almeida@lms.com', '12987654321', '12345678', 'Rua Secundária, 789', 'São José', 'MG'),
       ('0aaaac55-6a79-4721-817a-6fdff02a2c2c', 'b6952301-1426-4cca-9b21-72a30d0f9656', 'Ana', 'Paula', '2002-03-25',
        'ana.paula@lms.com', '13998765432', '12345678', 'Avenida Central, 101', 'Rio de Janeiro', 'RJ'),
       ('dd8a3f5e-9b60-4b32-91a9-8957ca439a48', '7f40510e-d073-4ab4-a99f-4a8051d8005d', 'Fernanda', 'Lima',
        '1999-11-30', 'fernanda.lima@lms.com', '14987654321', '12345678', 'Rua B, 202', 'Belo Horizonte', 'MG'),
       ('8be48326-9283-4aa9-b7ee-e6ec29b9c842', 'be021e76-e00a-48fe-b1d7-00cbe881ca89', 'Pedro', 'Costa', '2001-08-05',
        'pedro.costa@lms.com', '15998765432', '12345678', 'Rua C, 303', 'Curitiba', 'PR'),
       ('f5b4a066-2a5d-4b06-a138-86c573f26c7b', '2f995ec4-9e45-41b2-b689-8befdd0122d5', 'Camila', 'Santos',
        '2003-02-10', 'camila.santos@lms.com', '16987654321', '12345678', 'Avenida F, 404', 'Porto Alegre', 'RS'),
       ('0bc47e66-0d92-4094-ba04-599065c22b9c', '06bc7a27-ad04-4a64-81cd-426a0c8b17f4', 'Bruno', 'Pereira',
        '2000-09-12', 'bruno.pereira@lms.com', '17998765432', '12345678', 'Rua G, 505', 'Salvador', 'BA'),
       ('b711d984-9fc8-4261-addc-3d0f71edc9f6', 'fb8d364f-aa62-4aef-8fa5-dfc74b882c0e', 'Lara', 'Ribeiro', '1997-04-18',
        'lara.ribeiro@lms.com', '18987654321', '12345678', 'Rua H, 606', 'Recife', 'PE'),
       ('a7723861-1041-4207-8124-61033d413873', '8ea3678c-4731-4f12-825d-782950a57aff', 'Gabriel', 'Oliveira',
        '2004-07-22', 'gabriel.olivera@lms.com', '19998765432', '12345678', 'Avenida I, 707', 'Brasília', 'DF');

-- Inserção de cursos fictícios
INSERT INTO courses (id, name, description, duration_in_months)
VALUES ('900a1d0c-b9ec-4fc3-861d-06348b27e180', 'Introdução à Programação com Python', 'Curso básico para iniciantes',
        3),
       ('aa9a4769-6322-4c14-aa85-9f358f296fd0', 'Desenvolvimento Web com Angular',
        'Crie aplicações web modernas com Angular', 6),
       ('f0d6753f-e56f-45c9-9c86-beb5623c1193', 'Banco de Dados SQL', 'Fundamentos de bancos de dados relacionais', 4),
       ('a9b55dee-d82e-4d8f-b03a-4579c80ac339', 'Java para Desenvolvedores', 'Curso avançado de Java', 5),
       ('0cc718b7-ac8c-443a-9411-e251acb115ca', 'DevOps com Docker e Kubernetes',
        'Otimize o desenvolvimento e a implantação de aplicações', 6),
       ('765f2485-9e88-4f2b-bcc1-d104930d7307', 'Qualidade de Software', 'Aprenda testes, automação e boas práticas',
        3),
       ('dfec3474-d07e-4d94-85cd-5bf78990c64b', 'Cloud Computing com AWS', 'Gerencie serviços em nuvem com a AWS', 6),
       ('92bf60ed-6e51-4d14-8ec6-45af4a305574', 'Design UX/UI', 'Crie interfaces amigáveis e intuitivas', 4),
       ('fc4011e1-4c0f-416d-9709-99d4153fdc5a', 'JavaScript Moderno', 'Conheça as novas features do JavaScript', 3),
       ('53f1cb83-02cc-4727-935d-6281b9b4daee', 'Estruturas de Dados e Algoritmos',
        'Otimize a performance das suas aplicações', 6),
       ('d8383515-3b76-4955-b9dd-23380f3afacb', 'Inteligência Artificial para Iniciantes',
        'Primeiros passos em IA e Machine Learning', 5),
       ('6972fe9a-cb35-48fe-81c4-bf875c6c112a', 'API REST com Spring Boot', 'Desenvolva APIs robustas e seguras', 4),
       ('11fb8bdc-4ed5-4bc7-8582-b19550b2286d', 'Front-end com React', 'Construa interfaces dinâmicas com React', 6),
       ('c3609cd2-f7fd-4dfe-9fcd-c23affdabd3a', 'Metodologias Ágeis', 'Scrum e Kanban na prática', 3),
       ('4c7c0c1b-6dee-400b-ba14-f2fedd1b7e0c', 'Segurança da Informação', 'Conceitos e práticas essenciais', 6);

-- Inserção de matrículas (corrigido: use student_id correto)
INSERT INTO enrollments (id, student_id, course_id, enrollment_date, expiration_date, status)
VALUES ('fc192767-871f-4032-9a5c-9945acb70f4f', 'c0bda8af-3d05-4071-9ce2-d1b1ca27981a',
        '900a1d0c-b9ec-4fc3-861d-06348b27e180', '2025-01-10 10:00:00', '2025-07-10 10:00:00', 'IN_PROGRESS'),
       ('e46fa8f6-9788-4007-8835-07671c5994e3', 'c0bda8af-3d05-4071-9ce2-d1b1ca27981a',
        'aa9a4769-6322-4c14-aa85-9f358f296fd0', '2025-02-15 11:30:00', '2025-08-15 11:30:00', 'IN_PROGRESS'),
       ('ad88705e-cb36-43ee-9116-f5eac3c41b3f', 'f9669c1e-b9df-454e-8c7c-0837f8033a48',
        'a9b55dee-d82e-4d8f-b03a-4579c80ac339', '2025-03-20 12:45:00', '2025-09-20 12:45:00', 'COMPLETED'),
       ('700ea4c7-60b4-4a94-a47b-8a3041df0196', 'd6146d0c-c281-4e6d-b25f-2147a8cd0520',
        '0cc718b7-ac8c-443a-9411-e251acb115ca', '2024-05-01 09:00:00', '2024-11-01 09:00:00', 'EXPIRED'),
       ('b551ddd3-6a7c-4512-bebb-943233d46f16', '0aaaac55-6a79-4721-817a-6fdff02a2c2c',
        '765f2485-9e88-4f2b-bcc1-d104930d7307', '2025-06-05 14:00:00', '2025-12-05 14:00:00', 'NOT_STARTED');

-- Inserção de tarefas para a matrícula 'fc192767-871f-4032-9a5c-9945acb70f4f' (João)
INSERT INTO tasks (id, enrollment_id, task_category_id, description, log_date, time_spent_in_minutes)
VALUES ('6111fc41-da27-46ec-8b54-0644dbe18b57', 'fc192767-871f-4032-9a5c-9945acb70f4f',
        '5764c1f5-1daa-4706-9f3f-2fa4da39d239', 'Pesquisa sobre algoritmos de ordenação', '2025-03-01', 60),
       ('e2f767ed-51f3-4b09-a789-e20e491f6bb3', 'fc192767-871f-4032-9a5c-9945acb70f4f',
        '6ae652f8-49cd-42e6-b8cc-3d33506016ff', 'Prática de programação em Python', '2025-03-05', 90);

-- Inserção de tarefas para a matrícula 'ad88705e-cb36-43ee-9116-f5eac3c41b3f' (Maria - COMPLETED)
INSERT INTO tasks (id, enrollment_id, task_category_id, description, log_date, time_spent_in_minutes)
VALUES ('6ea16038-b7c6-46a4-8440-a62ab3c8f2b1', 'ad88705e-cb36-43ee-9116-f5eac3c41b3f',
        '393d5b8b-688a-4d9d-ab18-6264e736acc2', 'Assistir aula sobre testes unitários', '2025-05-20', 30);

-- Inserção de mais matrículas para os estudantes (corrigido: use student_id correto)
INSERT INTO enrollments (id, student_id, course_id, enrollment_date, expiration_date, status)
VALUES ('b285a94e-c551-427c-ae5d-f0f53344e3b5', 'f9669c1e-b9df-454e-8c7c-0837f8033a48',
        '6972fe9a-cb35-48fe-81c4-bf875c6c112a', '2025-01-20 15:00:00', '2025-07-20 15:00:00', 'IN_PROGRESS'),
       ('f97131a7-68a8-4979-8e74-3d37704c82e6', 'f9669c1e-b9df-454e-8c7c-0837f8033a48',
        '11fb8bdc-4ed5-4bc7-8582-b19550b2286d', '2025-02-25 09:00:00', '2025-08-25 09:00:00', 'IN_PROGRESS'),
       ('e3452a96-4425-4f5b-9412-0cd39946b32b', 'c0bda8af-3d05-4071-9ce2-d1b1ca27981a',
        'c3609cd2-f7fd-4dfe-9fcd-c23affdabd3a', '2024-11-10 14:00:00', '2025-05-10 14:00:00', 'EXPIRED'),
       ('b3f6c4a6-cdbc-459e-bbd6-142c0acd806e', '0aaaac55-6a79-4721-817a-6fdff02a2c2c',
        'a9b55dee-d82e-4d8f-b03a-4579c80ac339', '2025-04-01 16:30:00', '2025-10-01 16:30:00', 'IN_PROGRESS'),
       ('73772ee1-92ce-4386-a1ae-a76e6f08abb3', 'c0bda8af-3d05-4071-9ce2-d1b1ca27981a',
        '6972fe9a-cb35-48fe-81c4-bf875c6c112a', '2025-03-05 10:00:00', '2025-09-05 10:00:00', 'IN_PROGRESS'),
       ('183dcef8-b538-42c3-884e-c775a731c041', '0bc47e66-0d92-4094-ba04-599065c22b9c',
        'f0d6753f-e56f-45c9-9c86-beb5623c1193', '2025-04-18 11:00:00', '2025-10-18 11:00:00', 'IN_PROGRESS'),
       ('c1550548-55db-4589-9425-60a15c4a3f79', 'd6146d0c-c281-4e6d-b25f-2147a8cd0520',
        '92bf60ed-6e51-4d14-8ec6-45af4a305574', '2025-05-22 08:00:00', '2025-11-22 08:00:00', 'NOT_STARTED'),
       ('6629dc36-3858-4f7c-b96e-234ffb53cb08', 'a7723861-1041-4207-8124-61033d413873',
        'f0d6753f-e56f-45c9-9c86-beb5623c1193', '2025-01-30 13:00:00', '2025-07-30 13:00:00', 'IN_PROGRESS'),
       ('1a744c71-7f0c-421e-b67f-d8e1f8f9a9e2', 'f5b4a066-2a5d-4b06-a138-86c573f26c7b',
        'c3609cd2-f7fd-4dfe-9fcd-c23affdabd3a', '2025-03-12 14:00:00', '2025-09-12 14:00:00', 'COMPLETED'),
       ('e9f3c1d7-8c13-46d4-9262-41ea4a91b06c', '8be48326-9283-4aa9-b7ee-e6ec29b9c842',
        'f0d6753f-e56f-45c9-9c86-beb5623c1193', '2024-02-28 17:00:00', '2024-08-28 17:00:00', 'EXPIRED');

-- Inserção de mais tarefas para as matrículas
INSERT INTO tasks (id, enrollment_id, task_category_id, description, log_date, time_spent_in_minutes)
VALUES ('f3ab189e-e746-4afe-99cb-5f94ce31c096', 'b285a94e-c551-427c-ae5d-f0f53344e3b5',
        '5764c1f5-1daa-4706-9f3f-2fa4da39d239', 'Estudo sobre desenvolvimento de APIs REST', '2025-02-01', 90),
       ('1dfc7aa4-9542-4721-bbe3-5ea99db55d78', 'b285a94e-c551-427c-ae5d-f0f53344e3b5',
        '6ae652f8-49cd-42e6-b8cc-3d33506016ff', 'Criação de um projeto de teste', '2025-02-05', 120),
       ('f1c85a20-a73f-4a5d-ba22-20c547876b14', 'e46fa8f6-9788-4007-8835-07671c5994e3',
        '5764c1f5-1daa-4706-9f3f-2fa4da39d239', 'Leitura da documentação do Angular', '2025-03-01', 60),
       ('7bc1823c-fd06-4f2d-bbdd-181f09ff1534', 'ad88705e-cb36-43ee-9116-f5eac3c41b3f',
        '6ae652f8-49cd-42e6-b8cc-3d33506016ff', 'Prática de algoritmos de busca', '2025-04-10', 90),
       ('e946da9a-7c3e-46fd-83c6-b1555d0995aa', '73772ee1-92ce-4386-a1ae-a76e6f08abb3',
        '393d5b8b-688a-4d9d-ab18-6264e736acc2', 'Assistir videoaula sobre Spring Security', '2025-05-10', 30);