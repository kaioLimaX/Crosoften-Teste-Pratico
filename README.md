
# 📚 BookApp

## 📖 Resumo do Projeto

O **BookApp** é um aplicativo simples e funcional de gerenciamento de livros. Ele permite aos usuários:

- Listar livros disponíveis e visualizar seus detalhes.
- Excluir livros existentes.
- Buscar livros usando uma barra de pesquisa.
- Cadastrar novos livros.
- Gerenciar sua conta através de uma tela de **Profile**, que também inclui a opção de logout.

Para acessar o aplicativo, é necessário realizar o registro e login. O fluxo de autenticação garante a segurança e personalização da experiência do usuário.

---

## 🚀 Funcionalidades

- **Lista de Livros**: Exibe os livros disponíveis com opção de visualizar detalhes e excluir.
- **Busca Avançada**: Filtra livros de forma rápida e eficiente.
- **Cadastro de Livros**: Permite ao usuário adicionar seus próprios livros à lista.
- **Profile**: Resumo das funcionalidades do app e opção de logout.
- **Autenticação**: Registro e login obrigatórios para acesso ao app.

---

## 🛠️ Tecnologias Utilizadas

### 📱 **Desenvolvimento do App**:
- **Kotlin**: Linguagem de programação principal.
- **Arquitetura MVVM + Clean Architecture**: Estrutura escalável e organizada.
- **Jetpack Navigation**: Implementação de uma Single Activity com gerenciamento via `nav_graph`.
- **Glide**: Carregamento e gerenciamento eficiente de imagens.
- **PermissionX**: Gestão simplificada de permissões no Android.
- **SharedPreferences**: Armazenamento local de dados leves.

### 🌐 **Comunicação com API**:
- **API de Testes da Crosoften**: Comunicação com API RESTful para CRUD de livros.
- **Retrofit**: Consumo de APIs REST.
- **Interceptor**: Gerenciamento de headers e inserção de tokens em chamadas específicas.
- **Multipart Upload**: Implementação para envio de arquivos à API.

### ⚙️ **Gestão de Dependências**:
- **Koin**: Injeção de dependências leve e eficiente.

### ⚡ **Fluxos e Gerenciamento de Dados**:
- **Flow**: Controle reativo de dados.

### 🧪 **Testes Unitários**:
- **MockK**: Criação de mocks para testes unitários.

---

## 🧩 Desafios Enfrentados

Durante o desenvolvimento, os principais desafios foram:

1. **Adaptação ao Koin**: Habituado ao Hilt, precisei estudar a simplicidade e a eficiência do Koin.
2. **Envio de Arquivos via API**: Implementação de `Multipart` para upload de arquivos, algo novo para mim.
3. **Gerenciamento de Permissões**: Uso da biblioteca PermissionX para lidar de forma eficiente com permissões, simplificando cenários complexos.

Esses desafios proporcionaram um aprendizado significativo, ampliando minha experiência e conhecimento.

---

## 📷 Screenshots

> Insira aqui imagens do app: tela de login, lista de livros, detalhes, cadastro de livros, busca e perfil.
> [Descrição da Imagem](screen_login.jpg)

---

## 🔧 Ferramentas de Compilação e Versões

### 📚 Dependências do Projeto

- **Glide**: Gerenciamento de carregamento de imagens.
- **Retrofit**: Comunicação com APIs REST.
- **Koin**: Injeção de dependências leve e eficiente.
- **Logging Interceptor (OkHttp)**: Para monitorar requisições HTTP.
- **Navigation (Jetpack)**: Navegação entre Fragments e telas.
- **PermissionX**: Gerenciamento simplificado de permissões.

### 🧪 Dependências para Testes

- **Robolectric**: Testes unitários para o Android.
- **Koin Test**: Testes para injeção de dependências.
- **MockK**: Mocking para testes unitários.
- **LiveData Test (AndroidX)**: Garantir execução síncrona em testes unitários.
- **Kotlin Coroutines Test**: Suporte para testes com coroutines.

---

### 🛠 Ferramentas de Desenvolvimento

- **Android Studio**: `Android Studio Koala | 2024.1.1 (June 2024)`.
- **Gradle**: `7.x.x`.
- **Kotlin**: `1.9.x`.
- **Min SDK**: `21`.
- **Target SDK**: `34`.


---

## 📂 Estrutura do Projeto

A estrutura do projeto foi projetada para garantir escalabilidade e manutenibilidade:

```plaintext
```plaintext
📂 crosoftenteste
 ┣ 📂 data
 ┃ ┣ 📂 remote
 ┃ ┃ ┣ 📂 api
 ┃ ┃ ┃ ┣ 📂 interceptors  # Configuração de interceptadores para requisições
 ┃ ┃ ┃ ┗ ApiService      # Interface para chamadas à API
 ┃ ┃ ┣ 📂 dto
 ┃ ┃ ┃ ┣ 📂 book         # DTOs relacionados a livros
 ┃ ┃ ┃ ┣ 📂 file         # DTOs relacionados a arquivos
 ┃ ┃ ┃ ┗ 📂 user         # DTOs relacionados a usuários
 ┃ ┗ 📂 repository        # Implementação dos repositórios
 ┣ 📂 di                  # Configuração de injeção de dependências (Koin)
 ┣ 📂 domain
 ┃ ┣ 📂 repository        # Interfaces dos repositórios
 ┃ ┗ 📂 usecases          # Casos de uso
 ┣ 📂 presentation
 ┃ ┣ 📂 ui
 ┃ ┃ ┣ 📂 auth           # Fluxo de autenticação (login e registro)
 ┃ ┃ ┣ 📂 base           # Componentes base para reuso
 ┃ ┃ ┣ 📂 book           # Interface relacionada a livros
 ┃ ┃ ┣ 📂 main           # Componentes principais do app
 ┃ ┃ ┣ 📂 state          # Gerenciamento de estados
 ┃ ┃ ┗ 📂 util           # Utilitários da interface
```

### Descrição dos Diretórios

- **data**: Contém fontes de dados locais e remotos, incluindo a API e os repositórios que implementam as interfaces definidas na camada `domain`.
- **domain**: Responsável pela lógica de negócios e contém os modelos e casos de uso principais do aplicativo.
- **presentation**: Contém as camadas relacionadas à interface do usuário, incluindo Fragments, Activities e ViewModels.
- **di**: Configuração de injeção de dependências utilizando o Koin.
- **utils**: Classes utilitárias e funções auxiliares usadas em todo o projeto.
- **tests**: Contém testes unitários escritos com MockK e outras ferramentas de teste.
- **resources**: Arquivos de recursos como layouts XML, strings, drawables e outros itens relacionados à interface gráfica.

Essa estrutura foi projetada para seguir os princípios de **Clean Architecture**, mantendo uma separação clara de responsabilidades e facilitando a manutenção e evolução do projeto.

---

## 💡 Como Rodar o Projeto

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/kaioLimaX/Crosoften-Teste-Pratico/
   ```
2. **Abra o projeto no Android Studio**.
3. **Configure o ambiente** com as versões indicadas acima.
4. **Compile e execute o projeto** em um dispositivo/emulador.

---

## 📜 Licença

Este projeto é para fins de teste prático.
