# BookNest

Aplicação Web desenvolvida para o **Checkpoint 05 da materia DevOps**, com o objetivo de demonstrar a construção e publicação de uma aplicação Java integrada a serviços de nuvem da Microsoft Azure.

O **BookNest** é um sistema de gerenciamento de biblioteca que permite o cadastro e gerenciamento de autores e livros, além de autenticação de usuários.

A aplicação utiliza **Spring Boot**, **Thymeleaf**, **Spring Security**, **Spring Data JPA**, **Azure SQL Database**, **Azure App Service**, **Application Insights** e **GitHub Actions**.

Link para acessar a aplicação: https://booknest-mvc-rm561497.azurewebsites.net 

---

# 👩‍💻 Integrantes

| Nome | RM |
|---|---|
| Isis Macedo | 561497 |
| Ana Clara de Oliveira Nascimento | 561957 |
| Henrique Pereira | 565608 |
| Rafael Carvalho Meireles | 563413 |

---

# 🎯 Objetivo

O objetivo do BookNest é permitir o gerenciamento de livros e autores através de uma aplicação web hospedada na nuvem.

O projeto implementa:

- Cadastro de usuários;
- Login e logout;
- Autenticação com Spring Security;
- Senhas protegidas com BCrypt;
- Dashboard;
- CRUD completo de autores;
- CRUD completo de livros;
- Relacionamento entre autores e livros;
- Interface visual com Thymeleaf;
- API REST;
- Operações utilizando JSON;
- Persistência no Azure SQL Database;
- Deploy no Azure App Service;
- CI/CD com GitHub Actions;
- Monitoramento utilizando Application Insights.

---

# 🛠️ Tecnologias utilizadas

## Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate
- Maven

## Frontend

- Thymeleaf
- HTML5
- CSS3

## Banco de dados

- Microsoft Azure SQL Database
- Microsoft SQL Server
- JDBC

## Cloud e DevOps

- Microsoft Azure
- Azure CLI
- Azure Cloud Shell
- Azure SQL Server
- Azure SQL Database
- Azure App Service
- Azure Application Insights
- GitHub
- GitHub Actions

---

# 🏗️ Macroarquitetura

A arquitetura da solução é composta pelo GitHub, pipeline de CI/CD, aplicação Java hospedada no Azure App Service, Azure SQL Database e Application Insights.

```text
                         ┌─────────────────────┐
                         │       GitHub        │
                         │ Código + Workflow   │
                         └──────────┬──────────┘
                                    │
                                    │ Push main
                                    ▼
                         ┌─────────────────────┐
                         │   GitHub Actions    │
                         │                     │
                         │ Java 21             │
                         │ Maven Build         │
                         │ Deploy              │
                         └──────────┬──────────┘
                                    │
                                    ▼
┌──────────────┐         ┌─────────────────────┐
│   Usuário    │ HTTPS   │ Azure App Service   │
│  Navegador   │────────►│                     │
└──────────────┘         │      BookNest       │
                         │ Spring Boot / Java  │
                         └──────┬────────┬─────┘
                                │        │
                           JDBC │        │ Telemetria
                                ▼        ▼
                  ┌───────────────┐   ┌─────────────────┐
                  │   Azure SQL   │   │   Application   │
                  │   Database    │   │    Insights     │
                  │               │   │                 │
                  │ USUARIOS      │   │ Monitoramento   │
                  │ AUTORES       │   │ Logs / Métricas │
                  │ LIVROS        │   │ Requisições     │
                  └───────────────┘   └─────────────────┘
```

---

# 🗃️ Modelo de dados

O banco de dados possui três tabelas principais.

## USUARIOS

Armazena os usuários que possuem acesso à aplicação.

Principais campos:

```
ID
NOME
EMAIL
SENHA
```

A senha é armazenada utilizando hash BCrypt.

---

## AUTORES

Armazena os autores cadastrados.

Principais campos:

```
ID
NOME
NACIONALIDADE
DATA_NASCIMENTO
```

---

## LIVROS

Armazena os livros cadastrados.

Principais campos:

```
ID
TITULO
ISBN
GENERO
ANO_PUBLICACAO
AUTOR_ID
```

---

# 🔗 Relacionamento

Existe um relacionamento **1:N entre AUTORES e LIVROS**.

```
AUTORES
   1
   │
   │
   N
LIVROS
```

Um autor pode possuir vários livros, enquanto cada livro possui um autor.

A chave estrangeira utilizada é:

```
LIVROS.AUTOR_ID → AUTORES.ID
```

---

# 🖥️ Interface Web

O sistema possui telas visuais desenvolvidas utilizando Thymeleaf, HTML e CSS.

Principais rotas:

```
/login
/cadastro
/
/autores
/autores/novo
/autores/editar/{id}
/livros
/livros/novo
/livros/editar/{id}
```

O fluxo principal é:

```
Cadastro
   ↓
Login
   ↓
Dashboard
   ├── Autores
   │      ├── Cadastrar
   │      ├── Listar
   │      ├── Editar
   │      └── Excluir
   │
   └── Livros
          ├── Cadastrar
          ├── Listar
          ├── Editar
          └── Excluir
```

---

# 🔐 Segurança

A aplicação utiliza **Spring Security** para controle de acesso.

Foram implementados:

- Cadastro de usuário;
- Login personalizado;
- Logout;
- Proteção das páginas internas;
- Autenticação utilizando e-mail e senha;
- BCrypt para proteção das senhas.

As rotas de login, cadastro e arquivos estáticos são públicas.

As demais funcionalidades exigem autenticação.

---

# 🌐 API REST

Além da interface visual, o projeto disponibiliza operações REST utilizando JSON.

## Autores

Endpoint:

```
/api/autores
```

Operações:

```
GET /api/autores
GET /api/autores/{id}
POST /api/autores
PUT /api/autores/{id}
DELETE /api/autores/{id}
```

Exemplo de JSON:

```
{
  "nome": "George Orwell",
  "nacionalidade": "Britânica",
  "dataNascimento": "1903-06-25"
}
```

---

## Livros

Endpoint:

```
/api/livros
```

Operações:

```
GET /api/livros
GET /api/livros/{id}
POST /api/livros
PUT /api/livros/{id}
DELETE /api/livros/{id}
```

Exemplo:

```
{
  "titulo": "1984",
  "isbn": "9780451524935",
  "genero": "Distopia",
  "anoPublicacao": 1949,
  "autor": {
    "id": 1
  }
}
```

---

# ☁️ Configuração da infraestrutura Azure

A infraestrutura do projeto foi criada através do **Azure Cloud Shell utilizando Azure CLI**, seguindo o procedimento apresentado em aula.

---

# 1. Registrar os providers

Antes da criação dos recursos, foram registrados os providers necessários.

```
az provider register --namespace Microsoft.Web

az provider register --namespace Microsoft.Insights

az provider register --namespace Microsoft.OperationalInsights

az provider register --namespace Microsoft.ServiceLinker

az provider register --namespace Microsoft.Sql
```

Também foi adicionada a extensão do Application Insights:

```
az extension add --name application-insights
```

---

# 🗄️ Azure SQL Database

## 2. Criar o Resource Group do banco

Foi criado um Resource Group específico para o Azure SQL:

```
az group create \
  --name rg-sql-dimdim \
  --location northcentralus
```

Durante a criação do SQL Server foi utilizada a região:

```
chilecentral
```

---

## 3. Criar o Azure SQL Server

Por segurança, a senha administrativa não deve ser armazenada diretamente no repositório.

Ela pode ser solicitada no Cloud Shell:

```
read -s -p "Senha do administrador SQL: " SQL_ADMIN_PASSWORD
echo
```

Em seguida:

```bash
az sql server create \
  --name sql-server-dimdim-rm561497-chilecentral \
  --resource-group rg-sql-dimdim \
  --location chilecentral \
  --admin-user user-dimdim \
  --admin-password "$SQL_ADMIN_PASSWORD" \
  --enable-public-network true
```

Servidor criado:

```
sql-server-dimdim-rm561497-chilecentral
```

Endereço:

```
sql-server-dimdim-rm561497-chilecentral.database.windows.net
```

---

## 4. Criar o banco de dados

O banco `db-dimdim` foi criado utilizando o nível de serviço Basic.

```
az sql db create \
  --resource-group rg-sql-dimdim \
  --server sql-server-dimdim-rm561497-chilecentral \
  --name db-dimdim \
  --service-objective Basic \
  --backup-storage-redundancy Local \
  --zone-redundant false
```

---

## 5. Configurar o firewall

Para fins de testes e estudos, foi criada uma regra permitindo acesso público ao SQL Server.

```
az sql server firewall-rule create \
  --resource-group rg-sql-dimdim \
  --server sql-server-dimdim-rm561497-chilecentral \
  --name liberaGeral \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 255.255.255.255
```

> ⚠️ Essa configuração foi utilizada somente para fins acadêmicos e de testes. Em um ambiente de produção, o acesso ao banco deve ser restringido.

---

# 🧱 Criação das tabelas no Azure SQL

## 6. Utilizar PowerShell no Azure Cloud Shell

Para executar o DDL, foi utilizado o **PowerShell do Azure Cloud Shell**, conforme procedimento apresentado em aula.

O comando utilizado para conexão com o banco foi:

```
Invoke-Sqlcmd
```

---

## 7. Executar o DDL

O script completo também está disponível em:

```
scripts/ddl.sql
```

DDL utilizado:

```sql
CREATE TABLE AUTORES (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    NOME VARCHAR(150) NOT NULL,
    NACIONALIDADE VARCHAR(100),
    DATA_NASCIMENTO DATE
);

CREATE TABLE USUARIOS (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    NOME VARCHAR(150) NOT NULL,
    EMAIL VARCHAR(150) NOT NULL UNIQUE,
    SENHA VARCHAR(255) NOT NULL
);

CREATE TABLE LIVROS (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    TITULO VARCHAR(200) NOT NULL,
    ISBN VARCHAR(20),
    GENERO VARCHAR(100),
    ANO_PUBLICACAO INT,
    AUTOR_ID BIGINT NOT NULL,

    CONSTRAINT FK_LIVROS_AUTORES
        FOREIGN KEY (AUTOR_ID)
        REFERENCES AUTORES(ID)
);
```

No PowerShell do Cloud Shell, o script pode ser executado utilizando:

```
$senha = Read-Host "Senha do Azure SQL" -AsSecureString

$credencial = New-Object `
  System.Management.Automation.PSCredential(
    "user-dimdim",
    $senha
  )

Invoke-Sqlcmd `
  -ServerInstance "sql-server-dimdim-rm561497-chilecentral.database.windows.net" `
  -Database "db-dimdim" `
  -Credential $credencial `
  -Query @"

CREATE TABLE AUTORES (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    NOME VARCHAR(150) NOT NULL,
    NACIONALIDADE VARCHAR(100),
    DATA_NASCIMENTO DATE
);

CREATE TABLE USUARIOS (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    NOME VARCHAR(150) NOT NULL,
    EMAIL VARCHAR(150) NOT NULL UNIQUE,
    SENHA VARCHAR(255) NOT NULL
);

CREATE TABLE LIVROS (
    ID BIGINT IDENTITY(1,1) PRIMARY KEY,
    TITULO VARCHAR(200) NOT NULL,
    ISBN VARCHAR(20),
    GENERO VARCHAR(100),
    ANO_PUBLICACAO INT,
    AUTOR_ID BIGINT NOT NULL,

    CONSTRAINT FK_LIVROS_AUTORES
        FOREIGN KEY (AUTOR_ID)
        REFERENCES AUTORES(ID)
);

"@
```

Após a execução, foram criadas:

```
USUARIOS
AUTORES
LIVROS
```

---

# 🚀 Azure App Service

## 8. Definir as variáveis

Seguindo o padrão utilizado em aula, foram definidas as variáveis utilizadas nos comandos seguintes:

```
RESOURCE_GROUP_NAME="rg-BookNest-mvc"
WEBAPP_NAME="BookNest-mvc-rm561497"
APP_SERVICE_PLAN="BookNest-mvc"
LOCATION="chilecentral"
RUNTIME="JAVA:21-java21"
GITHUB_REPO_NAME="isismodd/Checkpoint05-Devops-JavaWebAPP"
BRANCH="main"
APP_INSIGHTS_NAME="ai-BookNest-mvc"
```

O exemplo apresentado em aula utilizava Java 17. O BookNest foi desenvolvido utilizando **Java 21**, portanto a runtime foi adaptada para:

```
JAVA:21-java21
```

---

## 9. Criar o Resource Group

```
az group create \
  --name $RESOURCE_GROUP_NAME \
  --location "$LOCATION"
```

---

# 📊 Application Insights

## 10. Criar o Application Insights

```
az monitor app-insights component create \
  --app $APP_INSIGHTS_NAME \
  --location "$LOCATION" \
  --resource-group $RESOURCE_GROUP_NAME \
  --application-type web
```

Recurso criado:

```
ai-BookNest-mvc
```

O Application Insights é utilizado para monitorar:

- Requisições;
- Falhas;
- Exceções;
- Tempo de resposta;
- Telemetria da aplicação.

---

# ⚙️ App Service Plan

## 11. Criar o plano

Foi utilizado um App Service Plan Linux no tier F1.

```
az appservice plan create \
  --name $APP_SERVICE_PLAN \
  --resource-group $RESOURCE_GROUP_NAME \
  --location "$LOCATION" \
  --sku F1 \
  --is-linux
```

---

# ☕ Web App

## 12. Criar o Web App

```
az webapp create \
  --name $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME \
  --plan $APP_SERVICE_PLAN \
  --runtime "$RUNTIME"
```

Para consultar as runtimes disponíveis:

```
az webapp list-runtimes
```

O Web App criado foi:

```
BookNest-mvc-rm561497
```

---

## 13. Habilitar autenticação básica SCM

Seguindo o procedimento apresentado em aula:

```
az resource update \
  --resource-group $RESOURCE_GROUP_NAME \
  --namespace Microsoft.Web \
  --resource-type basicPublishingCredentialsPolicies \
  --name scm \
  --parent sites/$WEBAPP_NAME \
  --set properties.allow=true
```

---

# 📊 Configuração do Application Insights

## 14. Recuperar a Connection String

```
CONNECTION_STRING=$(az monitor app-insights component show \
  --app $APP_INSIGHTS_NAME \
  --resource-group $RESOURCE_GROUP_NAME \
  --query connectionString \
  --output tsv)
```

A variável:

```
CONNECTION_STRING
```

passa a armazenar a Connection String do Application Insights.

---

# 🔌 Configuração do Azure SQL no Spring Boot

## 15. application.properties

O projeto utiliza variáveis de ambiente para que credenciais não sejam armazenadas diretamente no código.

```
spring.application.name=BookNest

spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

server.port=${PORT:8080}
```

A JDBC URL utilizada é:

```
jdbc:sqlserver://sql-server-dimdim-rm561497-chilecentral.database.windows.net:1433;databaseName=db-dimdim;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
```

---

# 🔐 Configurar App Settings

## 16. Configuração apresentada em aula

Seguindo o procedimento apresentado em aula, as variáveis podem ser configuradas com:

```
az webapp config appsettings set \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --settings \
    APPLICATIONINSIGHTS_CONNECTION_STRING="$CONNECTION_STRING" \
    ApplicationInsightsAgent_EXTENSION_VERSION="~3" \
    XDT_MicrosoftApplicationInsights_Mode="Recommended" \
    XDT_MicrosoftApplicationInsights_PreemptSdk="1" \
    SPRING_DATASOURCE_USERNAME="user-dimdim" \
    SPRING_DATASOURCE_PASSWORD="$SQL_ADMIN_PASSWORD" \
    SPRING_DATASOURCE_URL="jdbc:sqlserver://sql-server-dimdim-rm561497-chilecentral.database.windows.net:1433;databaseName=db-dimdim;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
```

---

# 🛠️ Problema encontrado durante o deploy

Durante a configuração do BookNest, o comando:

```
az webapp config appsettings set
```

apresentou erro relacionado à versão da Azure Management API utilizada pela Azure CLI.

A versão da Azure CLI utilizada no Cloud Shell tentou acessar uma versão de API que não estava disponível para o recurso naquele ambiente.

Por esse motivo, foi utilizada a Azure Management API diretamente através do comando:

```
az rest
```

com a versão:

```
2025-03-01
```

---

## 17. Recuperar a Subscription ID

```
SUBSCRIPTION_ID=$(az account show --query id -o tsv)
```

---

## 18. Montar o endereço de App Settings

```
APPSETTINGS_URI="https://management.azure.com/subscriptions/$SUBSCRIPTION_ID/resourceGroups/$RESOURCE_GROUP_NAME/providers/Microsoft.Web/sites/$WEBAPP_NAME/config/appsettings"
```

---

## 19. Recuperar as configurações existentes

Primeiro foram recuperadas as configurações que já existiam no Web App.

```
CURRENT_SETTINGS=$(az rest \
  --method POST \
  --uri "$APPSETTINGS_URI/list?api-version=2025-03-01" \
  --query properties \
  --output json)
```

Isso evita remover configurações existentes ao atualizar os App Settings.

---

## 20. Montar as novas configurações

```
BODY=$(echo "$CURRENT_SETTINGS" | jq \
  --arg insights "$CONNECTION_STRING" \
  --arg url "jdbc:sqlserver://sql-server-dimdim-rm561497-chilecentral.database.windows.net:1433;databaseName=db-dimdim;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" \
  --arg username "user-dimdim" \
  --arg password "$SQL_ADMIN_PASSWORD" \
  '. + {
    "APPLICATIONINSIGHTS_CONNECTION_STRING": $insights,
    "ApplicationInsightsAgent_EXTENSION_VERSION": "~3",
    "XDT_MicrosoftApplicationInsights_Mode": "Recommended",
    "XDT_MicrosoftApplicationInsights_PreemptSdk": "1",
    "SPRING_DATASOURCE_URL": $url,
    "SPRING_DATASOURCE_USERNAME": $username,
    "SPRING_DATASOURCE_PASSWORD": $password
  } | {properties: .}')
```

---

## 21. Enviar as configurações ao Web App

```
az rest \
  --method PUT \
  --uri "$APPSETTINGS_URI?api-version=2025-03-01" \
  --headers "Content-Type=application/json" \
  --body "$BODY"
```

Com isso, foram configuradas:

```
APPLICATIONINSIGHTS_CONNECTION_STRING
ApplicationInsightsAgent_EXTENSION_VERSION
XDT_MicrosoftApplicationInsights_Mode
XDT_MicrosoftApplicationInsights_PreemptSdk

SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

sem armazenar a senha diretamente no código-fonte.

---

# 🔄 Reiniciar o Web App

## 22. Reiniciar após as configurações

```
az webapp restart \
  --name $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME
```

---

# 📈 Conectar o Web App ao Application Insights

## 23. Criar a conexão

```
az monitor app-insights component connect-webapp \
  --app $APP_INSIGHTS_NAME \
  --web-app $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME
```

---

# 🔁 CI/CD com GitHub Actions

## 24. Configurar o GitHub Actions pelo Azure

Seguindo o procedimento apresentado em aula:

```
az webapp deployment github-actions add \
  --name $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME \
  --repo $GITHUB_REPO_NAME \
  --branch $BRANCH \
  --login-with-github
```

O deploy é realizado automaticamente quando alterações são enviadas para:

```
main
```

---

# 🔑 GitHub Secrets

## 25. Configurar os Secrets

No GitHub:

```
Settings
→ Secrets and variables
→ Actions
→ New repository secret
```

Foram adicionados:

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Também é utilizado o secret do **Publish Profile do Azure App Service**, gerado durante a configuração do deploy.

As credenciais não são armazenadas diretamente no workflow.

---

# ⚙️ Workflow

## 26. Variáveis no Maven Build

O workflow utiliza Java 21 e recebe as informações do banco através dos GitHub Secrets.

Exemplo do passo de build:

```
- name: Build with Maven
  run: mvn clean install -DskipTests
  env:
    SPRING_DATASOURCE_URL: ${{ secrets.SPRING_DATASOURCE_URL }}
    SPRING_DATASOURCE_USERNAME: ${{ secrets.SPRING_DATASOURCE_USERNAME }}
    SPRING_DATASOURCE_PASSWORD: ${{ secrets.SPRING_DATASOURCE_PASSWORD }}
```

Foi utilizado:

```
-DskipTests
```

conforme procedimento apresentado em aula.

O processo de CI/CD fica:

```
Alteração no código
        ↓
git push
        ↓
GitHub - main
        ↓
GitHub Actions
        ↓
Java 21
        ↓
Maven
mvn clean install -DskipTests
        ↓
JAR
        ↓
Azure App Service
        ↓
BookNest publicado
```

---

# 🚀 Deploy

## 27. Enviar alterações

Adicionar os arquivos:

```
git add .
```

Criar o commit:

```
git commit -m "Atualiza BookNest"
```

Caso existam alterações remotas:

```
git pull origin main --rebase
```

Enviar:

```
git push origin main
```

O push na `main` inicia automaticamente o GitHub Actions.

---

# ✅ Validação do GitHub Actions

No GitHub:

```
Actions
→ Build and deploy JAR app to Azure Web App
```

O pipeline executa:

```
Checkout
   ↓
Configuração Java 21
   ↓
Maven Build
   ↓
Deploy Azure App Service
```

Resultado esperado:

```
✓ Success
```

---

# 🧪 Validação da aplicação publicada

Após o deploy, foram testadas as principais funcionalidades da aplicação diretamente no Azure.

Fluxo testado:

```
Cadastro
   ↓
Login
   ↓
Dashboard
   ↓
Autores
   ├── Cadastrar
   ├── Listar
   ├── Editar
   └── Excluir
   ↓
Livros
   ├── Cadastrar
   ├── Listar
   ├── Editar
   └── Excluir
   ↓
Logout
```

A aplicação publicada realiza a persistência diretamente no **Azure SQL Database**, não dependendo de banco de dados local.

---

# 📊 Monitoramento

O projeto utiliza:

```
ai-BookNest-mvc
```

como recurso do Application Insights.

A integração permite monitorar a aplicação publicada e consultar informações relacionadas a requisições, falhas e desempenho.

---

# 📁 Estrutura do projeto

```
BookNest
│
├── .github/
│   └── workflows/
│
├── scripts/
│   ├── ddl.sql
│   └── azure-cli.sh
│
├── src/
│   └── main/
│       │
│       ├── java/
│       │   └── br/com/fiap/demo/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── model/
│       │       ├── repository/
│       │       └── service/
│       │
│       └── resources/
│           ├── static/
│           │   └── css/
│           │
│           ├── templates/
│           │   ├── auth/
│           │   ├── autores/
│           │   └── livros/
│           │
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

# 📜 Scripts

Os scripts utilizados para criação e reprodução do ambiente devem ser mantidos no diretório:

```
scripts/
```

Estrutura:

```
scripts/
├── ddl.sql
└── azure-cli.sh
```

O `ddl.sql` contém a criação das tabelas.
O `azure-cli.sh` contém os comandos utilizados para criação dos recursos Azure, sem credenciais sensíveis armazenadas diretamente no arquivo.
---

# 🏁 Conclusão

O BookNest implementa uma aplicação Java Web completa integrada aos serviços PaaS da Microsoft Azure.

A solução utiliza **Azure SQL Database** para persistência, **Azure App Service** para hospedagem, **Application Insights** para monitoramento e **GitHub Actions** para automatização do processo de build e deploy.

A infraestrutura foi criada utilizando **Azure CLI**, enquanto as tabelas foram criadas no Azure SQL através do PowerShell do Azure Cloud Shell utilizando `Invoke-Sqlcmd`.

O pipeline CI/CD permite que alterações enviadas para a branch `main` sejam compiladas utilizando Java 21 e Maven e posteriormente publicadas automaticamente no Azure App Service.
