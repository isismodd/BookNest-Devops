#!/bin/bash

# ============================================================
# BookNest - Script de criação da infraestrutura no Azure
#
# Recursos utilizados:
# - Azure SQL Server
# - Azure SQL Database
# - Azure App Service
# - Azure Application Insights
# - GitHub Actions
#
# IMPORTANTE:
# Nenhuma senha é armazenada diretamente neste arquivo.
# ============================================================


# ============================================================
# 1. REGISTRO DOS PROVIDERS
# ============================================================

az provider register --namespace Microsoft.Web

az provider register --namespace Microsoft.Insights

az provider register --namespace Microsoft.OperationalInsights

az provider register --namespace Microsoft.ServiceLinker

az provider register --namespace Microsoft.Sql


# Extensão utilizada para trabalhar com Application Insights
az extension add --name application-insights


# ============================================================
# 2. AZURE SQL DATABASE
# ============================================================

SQL_RESOURCE_GROUP="rg-sql-dimdim"
SQL_RESOURCE_GROUP_LOCATION="northcentralus"

SQL_SERVER_NAME="sql-server-dimdim-rm561497-chilecentral"
SQL_SERVER_LOCATION="chilecentral"

SQL_DATABASE_NAME="db-dimdim"
SQL_ADMIN_USER="user-dimdim"


# ------------------------------------------------------------
# Criar Resource Group do banco
# ------------------------------------------------------------

az group create \
  --name "$SQL_RESOURCE_GROUP" \
  --location "$SQL_RESOURCE_GROUP_LOCATION"


# ------------------------------------------------------------
# Solicitar a senha do administrador
#
# A senha não é armazenada no script.
# ------------------------------------------------------------

read -s -p "Digite a senha do administrador do Azure SQL: " SQL_ADMIN_PASSWORD
echo


# ------------------------------------------------------------
# Criar Azure SQL Server
# ------------------------------------------------------------

az sql server create \
  --name "$SQL_SERVER_NAME" \
  --resource-group "$SQL_RESOURCE_GROUP" \
  --location "$SQL_SERVER_LOCATION" \
  --admin-user "$SQL_ADMIN_USER" \
  --admin-password "$SQL_ADMIN_PASSWORD" \
  --enable-public-network true


# ------------------------------------------------------------
# Criar Azure SQL Database
# ------------------------------------------------------------

az sql db create \
  --resource-group "$SQL_RESOURCE_GROUP" \
  --server "$SQL_SERVER_NAME" \
  --name "$SQL_DATABASE_NAME" \
  --service-objective Basic \
  --backup-storage-redundancy Local \
  --zone-redundant false


# ------------------------------------------------------------
# Liberar acesso público
#
# ATENÇÃO:
# Configuração utilizada apenas para fins acadêmicos,
# testes e estudos.
# ------------------------------------------------------------

az sql server firewall-rule create \
  --resource-group "$SQL_RESOURCE_GROUP" \
  --server "$SQL_SERVER_NAME" \
  --name liberaGeral \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 255.255.255.255


# ============================================================
# 3. VARIÁVEIS DO AZURE APP SERVICE
# ============================================================

RESOURCE_GROUP_NAME="rg-BookNest-mvc"
WEBAPP_NAME="BookNest-mvc-rm561497"
APP_SERVICE_PLAN="BookNest-mvc"

LOCATION="chilecentral"
RUNTIME="JAVA:21-java21"

GITHUB_REPO_NAME="isismodd/BookNest-Devops"
BRANCH="main"

APP_INSIGHTS_NAME="ai-BookNest-mvc"


# ============================================================
# 4. CRIAR RESOURCE GROUP DA APLICAÇÃO
# ============================================================

az group create \
  --name "$RESOURCE_GROUP_NAME" \
  --location "$LOCATION"


# ============================================================
# 5. APPLICATION INSIGHTS
# ============================================================

az monitor app-insights component create \
  --app "$APP_INSIGHTS_NAME" \
  --location "$LOCATION" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --application-type web


# ============================================================
# 6. APP SERVICE PLAN
# ============================================================

az appservice plan create \
  --name "$APP_SERVICE_PLAN" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --location "$LOCATION" \
  --sku F1 \
  --is-linux


# ============================================================
# 7. AZURE WEB APP
# ============================================================

az webapp create \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --plan "$APP_SERVICE_PLAN" \
  --runtime "$RUNTIME"


# ============================================================
# 8. HABILITAR AUTENTICAÇÃO BÁSICA SCM
# ============================================================

az resource update \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --namespace Microsoft.Web \
  --resource-type basicPublishingCredentialsPolicies \
  --name scm \
  --parent "sites/$WEBAPP_NAME" \
  --set properties.allow=true


# ============================================================
# 9. APPLICATION INSIGHTS CONNECTION STRING
# ============================================================

CONNECTION_STRING=$(az monitor app-insights component show \
  --app "$APP_INSIGHTS_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --query connectionString \
  --output tsv)


# ============================================================
# 10. JDBC AZURE SQL
# ============================================================

SPRING_DATASOURCE_URL="jdbc:sqlserver://${SQL_SERVER_NAME}.database.windows.net:1433;databaseName=${SQL_DATABASE_NAME};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"


# ============================================================
# 11. CONFIGURAÇÃO DOS APP SETTINGS
#
# Durante o desenvolvimento, o comando:
#
# az webapp config appsettings set
#
# apresentou incompatibilidade de versão da Azure Management
# API no ambiente utilizado.
#
# Por isso, foi utilizado az rest com api-version=2025-03-01.
# ============================================================


# Recuperar ID da assinatura
SUBSCRIPTION_ID=$(az account show \
  --query id \
  --output tsv)


# Endpoint dos App Settings
APPSETTINGS_URI="https://management.azure.com/subscriptions/$SUBSCRIPTION_ID/resourceGroups/$RESOURCE_GROUP_NAME/providers/Microsoft.Web/sites/$WEBAPP_NAME/config/appsettings"


# Recuperar configurações atuais
CURRENT_SETTINGS=$(az rest \
  --method POST \
  --uri "$APPSETTINGS_URI/list?api-version=2025-03-01" \
  --query properties \
  --output json)


# Adicionar configurações do banco e Application Insights
# sem apagar configurações já existentes
BODY=$(echo "$CURRENT_SETTINGS" | jq \
  --arg insights "$CONNECTION_STRING" \
  --arg url "$SPRING_DATASOURCE_URL" \
  --arg username "$SQL_ADMIN_USER" \
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


# Enviar configurações
az rest \
  --method PUT \
  --uri "$APPSETTINGS_URI?api-version=2025-03-01" \
  --headers "Content-Type=application/json" \
  --body "$BODY"


# ============================================================
# 12. REINICIAR O WEB APP
# ============================================================

az webapp restart \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME"


# ============================================================
# 13. CONECTAR APPLICATION INSIGHTS AO WEB APP
# ============================================================

az monitor app-insights component connect-webapp \
  --app "$APP_INSIGHTS_NAME" \
  --web-app "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME"


# ============================================================
# 14. CONFIGURAR GITHUB ACTIONS
# ============================================================

az webapp deployment github-actions add \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --repo "$GITHUB_REPO_NAME" \
  --branch "$BRANCH" \
  --login-with-github


# ============================================================
# FINALIZAÇÃO
# ============================================================

echo
echo "============================================================"
echo " Infraestrutura do BookNest configurada."
echo "============================================================"
echo
echo "Resource Group App: $RESOURCE_GROUP_NAME"
echo "Web App: $WEBAPP_NAME"
echo "Application Insights: $APP_INSIGHTS_NAME"
echo
echo "Resource Group SQL: $SQL_RESOURCE_GROUP"
echo "SQL Server: $SQL_SERVER_NAME"
echo "Database: $SQL_DATABASE_NAME"
echo
echo "Próximos passos:"
echo "1. Executar scripts/ddl.sql no Azure SQL Database."
echo "2. Configurar os GitHub Secrets."
echo "3. Conferir o workflow em .github/workflows/."
echo "4. Fazer push para a branch main."
