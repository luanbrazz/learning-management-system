# LMS - Learning Management System

Este projeto é uma aplicação **full stack** desenvolvida como parte de um desafio técnico para a empresa GFT. O objetivo é criar um sistema simples de gerenciamento de aprendizagem que permite a administração de cursos e o acompanhamento do progresso de estudantes.

## Funcionalidades Implementadas

O sistema foi construído para atender a todas as regras de negócio solicitadas, com uma arquitetura robusta e escalável.

### **Funcionalidade 1: Gerenciamento de Estudantes**
* **Registro de Estudantes:** Endpoint público para o cadastro de novos estudantes com validações de idade mínima (16 anos) e unicidade de e-mail e nome de usuário.
* **Detalhes do Estudante:** O registro inclui dados pessoais e de endereço, com preenchimento automático por CEP via integração com a API ViaCEP.

### **Funcionalidade 2: Gerenciamento de Cursos**
* **CRUD Completo:** Administradores podem criar, visualizar, editar e remover cursos.
* **Regras de Negócio:** O nome do curso é único e a duração é limitada a 6 meses.
* **Controle de Acesso:** Apenas usuários com a role `ROLE_ADMIN` podem gerenciar os cursos.

### **Funcionalidade 3: Matrículas e Tarefas**
* **Matrícula em Cursos:** Estudantes podem se matricular em cursos, com a regra de negócio de um limite de 3 matrículas ativas por estudante.
* **Registro de Tarefas:** Estudantes podem registrar tarefas para os cursos em que estão matriculados. O tempo gasto é registrado em incrementos de 30 minutos.
* **Status de Matrícula:** O status da matrícula é atualizado automaticamente (`NOT_STARTED`, `IN_PROGRESS`) e pode ser manualmente alterado para `COMPLETED` pelo estudante.
* **Gestão de Matrículas Expiradas:** O administrador pode marcar matrículas como `EXPIRED` se o prazo de 6 meses for excedido e o curso não tiver sido concluído.

## Tecnologias Utilizadas

### **Backend (Java)**
* **Java 21**: Linguagem de programação.
* **Spring Boot 3.4**: Framework para a construção da API.
* **Spring Data JPA**: Para abstração e acesso ao banco de dados relacional.
* **Flyway**: Gerenciamento de migrações de banco de dados.
* **Spring Security**: Autenticação e autorização com JWT.
* **Lombok**: Geração automática de código.
* **PostgreSQL**: Banco de dados relacional.
* **RESTful API**: Arquitetura da API.
* **i18n**: Internacionalização das mensagens de erro e validação.

### **Frontend (Angular)**
* **Angular 19**: Framework para a construção da interface do usuário.
* **Componentes Standalone**: Estrutura de componentes moderna e modular.
* **RxJS**: Para gerenciamento de requisições assíncronas e fluxo de dados reativo.
* **Bootstrap**: Framework CSS para o design da interface.
* **ngx-toastr**: Biblioteca para notificações de sucesso e erro.
* **Guards de Rotas**: Para proteger as rotas da aplicação com base na autenticação e nas roles do usuário.

## Arquitetura do Projeto

O projeto foi construído com uma arquitetura modular e escalável, com uma clara separação de responsabilidades:
* **Classes Genéricas (`BaseService` e `BaseController`)**: Reduzem a duplicação de código e facilitam a adição de novas entidades.
* **DTOs (Data Transfer Objects)**: Garantem um contrato seguro e limpo entre o frontend e o backend, evitando a exposição de entidades JPA.
* **`AuthInterceptor` no Angular**: Adiciona automaticamente o token JWT a todas as requisições, tornando a comunicação com a API segura.
* **`GlobalExceptionHandler` no Java**: Centraliza o tratamento de erros da API, retornando respostas padronizadas e amigáveis.

## Como Executar o Projeto

### Pré-requisitos
* Java 21+
* Node.js e npm
* PostgreSQL
* Angular CLI

### Configuração do Banco de Dados
1.  Certifique-se de que o PostgreSQL está rodando.
2.  Crie um banco de dados chamado `lms` e um usuário com permissões de acesso.
3.  No arquivo `backend/src/main/resources/application.properties`, configure as credenciais do seu banco.

### Executando o Backend
1.  Vá para a pasta do backend: `cd backend`
2.  Inicie a aplicação: `mvn spring-boot:run`
    * O Flyway executará automaticamente as migrações e criará a base de dados, incluindo os dados de roles e categorias de tarefas.

### Executando o Frontend
1.  Vá para a pasta do frontend: `cd frontend`
2.  Instale as dependências: `npm install`
3.  Inicie a aplicação: `ng serve --open`

### Testando a Aplicação
* Abra a aplicação no navegador em `http://localhost:4200`.
* Use o formulário de registro para criar um novo estudante.
* Faça o login com o usuário e a senha criados.
* Use as credenciais de administrador (`admin@email.com` e `admin123`) para fazer o login e acessar o painel de administração.

## Oportunidades de Melhoria

O projeto atual atende a todos os requisitos, mas, em um cenário de produção, as seguintes melhorias seriam consideradas:
* **Arquitetura de Microserviços:** Conforme o sistema cresce, a aplicação pode ser dividida em microserviços (por exemplo, um serviço de `Auth`, um de `Courses`, e um de `Enrollments`) para melhor escalabilidade e manutenção.
* **Notificações em Tempo Real:** Integrar um WebSocket para notificar o estudante sobre atualizações de status ou novas tarefas.
* **Testes:** Adicionar testes de integração e testes de unidade mais robustos para o frontend e o backend.
* **Experiência do Usuário (UX):** Melhorar o design do formulário e a experiência do usuário com validações em tempo real e feedbacks mais detalhados.
* **Validação de CEP:** Adicionar a validação do CEP no backend para garantir que apenas CEPs válidos sejam enviados.
