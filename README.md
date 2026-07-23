<p align="center">
  <img src="docs/imagens/logo.png" width="180" alt="MS Almoxarife">
</p>

<h1 align="center">
MS Almoxarife
</h1>

<p align="center">
Sistema Inteligente de Gestão de Almoxarifado Público MS
</p>

<p align="center">
Desenvolvido pela <strong>MS Soluções Inteligentes</strong>
</p>

---

# 📌 Sobre o Projeto

O **MS Almoxarife** é um sistema desenvolvido para informatizar e modernizar o controle de almoxarifados públicos.

Foi projetado para atender:

- Prefeituras
- Câmaras Municipais
- Autarquias
- Consórcios Públicos
- Secretarias Municipais

O sistema oferece controle completo de estoque, entradas, saídas, fornecedores, usuários, lotes, auditoria e indicadores gerenciais em tempo real.

---

# 🚀 Funcionalidades

- ✅ Cadastro de Produtos
- ✅ Cadastro de Categorias
- ✅ Cadastro de Fornecedores
- ✅ Cadastro de Setores
- ✅ Cadastro de Usuários
- ✅ Cadastro de Prefeituras
- ✅ Controle de Estoque
- ✅ Controle por Lotes
- ✅ Entrada de Produtos
- ✅ Saída de Produtos
- ✅ Dashboard Inteligente
- ✅ Auditoria de Movimentações
- ✅ Relatórios
- ✅ API REST
- ✅ Autenticação JWT
- ✅ Controle de Permissões
- ✅ Sistema Multi Prefeitura

---

# 🛠 Tecnologias

<p>

<img src="https://img.shields.io/badge/Java-21-red">

<img src="https://img.shields.io/badge/Spring_Boot-3.5-green">

<img src="https://img.shields.io/badge/React-19-blue">

<img src="https://img.shields.io/badge/Vite-8-purple">

<img src="https://img.shields.io/badge/MySQL-8-orange">

<img src="https://img.shields.io/badge/Docker-✓-blue">

<img src="https://img.shields.io/badge/Caddy-2-darkgreen">

<img src="https://img.shields.io/badge/JWT-Security-black">

</p>

---

# 📷 Telas do Sistema

## Login

![](docs/imagens/login.png)

---

## Dashboard

![](docs/imagens/dashboard.png)

---

## Produtos

![](docs/imagens/produtos.png)

---

## Movimentações

![](docs/imagens/movimentacoes.png)

---

## Fornecedores

![](docs/imagens/fornecedores.png)

---

## Setores

![](docs/imagens/setores.png)

---

## Auditoria

![](docs/imagens/auditoria.png)

---

# 📁 Estrutura do Projeto

```
sistema-almoxarifado/

├── backend/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   ├── Dockerfile
│   └── nginx.conf
│
├── docs/
│   ├── imagens/
│   ├── backup.md
│   ├── deploy.md
│   ├── atualizacao.md
│   └── banco.md
│
├── backups/
│
├── docker-compose.yml
│
├── Caddyfile
│
└── README.md
```

---

# ⚙️ Instalação

Clone o projeto

```bash
git clone https://github.com/Juniorjoelpt/sistema-almoxarifado.git
```

Entre na pasta

```bash
cd sistema-almoxarifado
```

Suba os containers

```bash
docker compose up -d --build
```

Acesse

```
https://seu-dominio.com
```

---

# 🔄 Atualização do Sistema

Atualize o projeto

```bash
git pull origin main
```

Recompile apenas os serviços alterados

```bash
docker compose up -d --build
```

---

# 💾 Backup

Para criar backup completo do sistema:

```bash
tar -czvf sistema-almoxarifado.tar.gz sistema-almoxarifado
```

Dump do banco:

```bash
mysqldump -u root -p almoxarifado > backup.sql
```

Documentação completa:

```
docs/backup.md
```

---

# 🚀 Deploy

O sistema está preparado para produção utilizando:

- Docker
- Docker Compose
- Nginx
- Caddy
- HTTPS automático
- SSL automático
- Spring Boot
- React + Vite

Consulte:

```
docs/deploy.md
```

---

# 📚 Documentação

Toda documentação técnica encontra-se em:

```
docs/
```

Incluindo:

- Instalação
- Deploy
- Atualizações
- Backup
- Banco de Dados
- API

---

# 🔐 Segurança

- Autenticação JWT
- Controle de perfis de acesso
- Auditoria de movimentações
- Controle de permissões
- HTTPS automático com Caddy

---

# 👨‍💻 Desenvolvedor

**MS Soluções Inteligentes**

Sistema desenvolvido para modernização da gestão pública.

---

# 📄 Licença

© MS Soluções Inteligentes

Todos os direitos reservados.

Este projeto não possui licença de uso livre.