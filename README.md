# 📋 Sistema de Inspeções Acadêmicas IBMEC

![Kotlin](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge\&logo=kotlin\&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge\&logo=android\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge\&logo=spring\&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge\&logo=mysql\&logoColor=white)

## 📖 Sobre o Projeto

O Sistema de Inspeções Acadêmicas foi desenvolvido para auxiliar inspetores na realização e no acompanhamento de inspeções de aulas dentro da instituição.

A aplicação permite registrar a presença dos professores em cada aula, identificar ausências justificadas e não justificadas, gerar estatísticas da inspeção realizada e consultar o histórico de inspeções finalizadas.

O projeto é composto por:

* Aplicativo Android desenvolvido em Kotlin
* API REST desenvolvida com Spring Boot
* Banco de dados MySQL
* Integração entre aplicativo e backend utilizando Retrofit

---

## 🚀 Funcionalidades

### 🔐 Autenticação

* Login de inspetores

### 📝 Registro de Inspeções

* Criação de novas inspeções
* Seleção de data e turno
* Registro da situação de cada professor

### 📊 Resumo da Inspeção

* Quantidade de presenças
* Quantidade de ausências justificadas
* Quantidade de ausências sem justificativa
* Taxa de faltas
* Indicadores visuais de desempenho

### 📤 Compartilhamento de Relatórios

* Compartilhamento do resumo da inspeção através de aplicativos compatíveis do dispositivo (Intent Implícita)

### 📚 Histórico

* Consulta de inspeções finalizadas
* Visualização do resumo completo de inspeções anteriores

---

## 🛠️ Tecnologias Utilizadas

### Mobile

* Kotlin
* Android SDK
* RecyclerView
* Fragments
* Retrofit
* Gson

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Swagger / OpenAPI

### Banco de Dados

* MySQL

### Hospedagem

* Render (API)
* MySQL em nuvem

---

## 📂 Estrutura Geral

### Aplicativo Android

Responsável pela interface do usuário e comunicação com a API.

Principais telas:

* Login
* Home
* Nova Inspeção
* Lista de Aulas
* Registro de Presença
* Resumo da Inspeção
* Histórico de Inspeções
* Detalhes de Inspeções Finalizadas

### API REST

Responsável por:

* Gerenciamento de usuários
* Gerenciamento de inspeções
* Registro de presenças
* Cálculo de métricas e estatísticas

---

## ⚙️ Como Executar

### Backend

1. Clone o repositório
2. Configure as credenciais do banco de dados
3. Execute a aplicação Spring Boot

A API será iniciada em:

```bash
http://localhost:8080
```

### Aplicativo Android

1. Abra o projeto no Android Studio
2. Configure a URL da API no arquivo `ApiClient.kt`
3. Execute em um dispositivo físico ou emulador

---

## 📖 Documentação da API

A documentação completa da API está disponível através do Swagger.

O link público da documentação encontra-se na descrição deste repositório.

---

## 🎓 Projeto Acadêmico

Projeto desenvolvido para a disciplina de Desenvolvimento Mobile do IBMEC.

### Conceitos aplicados

* Desenvolvimento Android Nativo
* Consumo de APIs REST
* Arquitetura Cliente-Servidor
* Persistência de Dados
* Integração Mobile + Backend
* RecyclerView
* Fragments
* Intents Explícitas e Implícitas
* Hospedagem em Nuvem
