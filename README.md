# O desafio (Consulta de Solicitações de Viagens)

Você deverá criar uma aplicação consumidora de nossa API de webservice para consultar solicitações de viagens e persistir em banco de dados os dados de produtos Aéreos:

**Endpoint:** https://treinamento.lemontech.com.br/wsselfbooking/WsSelfBookingService?wsdl  
**Método:** `pesquisarSolicitacao`

---

# Fullstack Java Teste

Projeto desenvolvido para demonstrar habilidades em desenvolvimento fullstack com **Java** no backend e tecnologias web no frontend.  
A aplicação consome uma **API SOAP** para buscar solicitações de viagens, armazena dados em um banco **MySQL** e expõe endpoints REST.

---

## Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot**
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - `lombok`
- **Maven**
- **MySQL**

### Frontend
- **HTML, CSS, JavaScript**
- **Fetch API**

---

## Estrutura do Projeto

Arquitetura **MVC** dividindo responsabilidades para manutenção e escalabilidade.

    fullstack-java-teste/
    ├── Backend/                         
    │   ├── src/
    │   │   ├── main/java/com/testelemontech/solicitacoes/
    │   │   │   ├── controller/     # Define os endpoints da API
    │   │   │   ├── service/        # Regras de negócio e chamadas à API SOAP
    │   │   │   ├── repository/     # Persistência de dados (Spring Data JPA)
    │   │   │   ├── model/          # Entidades que representam tabelas do banco
    │   │   │   ├── config/         # Configuração do Spring Boot e integração SOAP
    │   ├── resources/
    │       ├── application.properties  # Configurações gerais do projeto
    │   ├── pom.xml                 # Dependências do Maven
    └── Frontend/                    
        ├── css/                     # Estilos (CSS)
        ├── js/                      # Scripts para manipulação da API
        ├── home.html                # Página principal com listagem de solicitações
        ├── create.html              # Página para criar solicitações fictícias


---

Após o consumo da API SOAP pelo método `pesquisarSolicitacao`, o serviço realiza os seguintes passos:

1. **Filtragem Temporal:** Seleciona apenas as solicitações criadas nos últimos 3 meses.
2. **Filtragem por Produto:** Dentre as solicitações filtradas, seleciona aquelas que contêm produtos Aéreos.
3. **Extração de Dados Essenciais:** Para cada solicitação filtrada, extrai informações como Nome do Passageiro, CIA Aérea, Data/Hora de Saída e Chegada, Cidades de Origem e Destino.
4. **Persistência:** Os dados extraídos são enviados à camada de repositório para serem persistidos no banco de dados MySQL.

### Diagrama de Arquitetura

```
graph TD
    A[Início] -->|Requisição SOAP| B[API SOAP]
    B -->|Consulta de Solicitações| C[Service - Sincronização]
    C -->|Filtragem e Persistência| D[Banco de Dados MySQL]
    D -->|Disponibilização de Dados| E[Endpoints REST]
    E -->|Consumo de Dados| F[Interface do Usuário]
```
---

## Como Executar o Projeto

### Pré-requisitos
- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/installer/)
- [Postman](https://www.postman.com/) (opcional)

### Configuração das Variáveis de Ambiente
Defina as seguintes variáveis antes de rodar o backend:
```sh
export MYSQL_HOST= host_local
export MYSQL_PORT= porta_local
export MYSQL_DB= schema mysql
export MYSQL_USER= seu_usuario
export MYSQL_PASSWORD= sua_senha
export SERVER_PORT=8081
export SOAP_USERNAME=seu_usuario
export SOAP_PASSWORD=sua_senha
export SOAP_WSDL_URL=https://treinamento.lemontech.com.br/wsselfbooking/WsSelfBookingService?wsdl
```

### Rodando o Backend
1. Clone o repositório:
   ```sh
   git clone https://github.com/MatheusHyago/fullstack-java-teste.git
   ```
2. Entre na pasta do backend:
   ```sh
   cd fullstack-java-teste/Backend
   ```
3. Compile e execute:
   ```sh
   mvn clean install
   ```
      ```sh
   mvn spring-boot:run
   ```
4. Teste a API em:
   
http://localhost:8081/solicitacoes
   
http://localhost:8081/solicitacoes/sincronizar

### Rodando o Frontend
1. Abra o command prompt do Node.js e entre na pasta:
   ```sh
   cd C:\Users\UserFicticio\fullstack-java-teste\Frontend\Server
   ```
2. Execute o comando:
   ```sh
   npm start
   ```
3. O servidor rodará na porta 3000 como localhost:
   ```sh
   http://localhost:3000
   ```
