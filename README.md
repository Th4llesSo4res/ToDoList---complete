# ToDoList - Guia de Instalação e Execução

## Tecnologias Utilizadas
- Java 17 (ou superior)
- Spring Boot
- Maven
- MySQL
- HTML, CSS e JavaScript (Frontend)

## Pré-Requisitos
- Java 17 instalado
- Maven instalado e configurado
- MySQL instalado e rodando
- Editor de código (VS Code, IntelliJ ou Eclipse)
- Extensão Live Server (opcional) para rodar o frontend

## Configuração do Banco de Dados
1. Abra seu MySQL.
2. Crie um banco de dados chamado 'todolist':
   ```sql
   CREATE DATABASE todolist;
3. após criar o banco de dados, no "arquivo backend/src/main/resources/application.properties"
   configure seu usuário e senha:
   spring.datasource.username=SEU_USUARIO

   spring.datasource.password=SUA_SENHA

## Executando Backend
1. Acesse a pasta "backend" Do projeto. (cd "C:\Users\Desktop\Desktop\ToDoList - API\ToDoList--Backend\backend\backend")
2. Execute o comando:

   mvn spring-boot:run
   
(Obs: certifique-se de que o Todas as configurações citadas anteriormente estejam funcionando, caso contrário vai dar erro.)

(Obs²: caso dê erro de proteção aos scripts no terminal, Normalmente ocorre no powershel, basta executar o comando: "Set-ExecutionPolicy RemoteSigned" e em seguida confirma digitando "S", e repetir novamente o mvn)
   

4. O backend rodará em "http://localhost:8080" (Tomcat started on port 8080 (http) with context path '/'....)

## Executando o Frontend
1. Acesse a pasta "Frontend"
2. Abra o arquivo "Cadastro.html" e escolha a opção "Open with Live Server";
3. O frontend estará disponível e conectado ao backend.

## Funcionalidades

1. Cadastro de usuários.
2. Login com autenticação via JWT.
3. Criação de tarefas com título, descrição, data e etiqueta.
4. Edição, conclusão e exclusão de tarefas.
5. Filtros por status e ordenação A-Z ou Z-A.
6. Cada usuário vê apenas suas próprias tarefas.

## Observações
- O backend precisa estar rodando antes de abrir o frontend.
- Não abra os arquivos HTML diretamente pelo navegador (file://). Use um servidor local como Live Server.
