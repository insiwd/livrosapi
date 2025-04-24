
# Livros API

> Livros API é uma aplicação que permite buscar, listar e gerenciar informações de livros, autores e idiomas, com dados provenientes da API do Projeto Gutendex. O foco é praticar habilidades de backend em Java, além de consolidar conhecimentos em consumo de APIs REST, persistência de dados e boas práticas de desenvolvimento.

## 📚 Funcionalidades

- Buscar livros por título
- Armazenar livros em um repositório local
- Listar livros salvos
- Listar autores e seus anos de nascimento/morte
- Filtrar livros por idioma
- Ordenar por título ou autor

## 🎓 Aprendizados

Durante o desenvolvimento da **Livros API**, os seguintes conhecimentos e habilidades foram reforçados:

- **Consumo de APIs REST**: Prática com requisições HTTP, tratamento de respostas e parsing de dados JSON utilizando bibliotecas como Jackson ou Gson.
- **Modelagem de dados**: Criação de classes que representam entidades como Livro e Autor, baseadas na estrutura da API externa.
- **Manipulação de coleções em Java**: Uso de `List`, `Map`, `Set` e operações com `Stream API` para filtragem, ordenação e agrupamento de dados.
- **Boas práticas de orientação a objetos**: Aplicação de princípios como encapsulamento, responsabilidade única e reutilização de código.
- **Organização de código**: Separação de responsabilidades por pacotes (`service`, `model`, `util`, etc.), tornando o projeto mais legível e escalável.
- **Uso de Maven**: Gerenciamento de dependências e automação do build do projeto.
- **Trabalho com dados externos**: Integração com a API do Projeto Gutendex, aprendendo a lidar com formatos variados e inconsistências de dados.


## 📦 Instalação

```
mvn clean install
mvn spring-boot:run
```

## 💻 Pré Requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:

```
- Java 17+
- Maven ou Gradle
- Você tem uma máquina Windows / Linux / Mac
- Você conhece o git
```

## 📫 Contribuindo para Livros API

Para contribuir com Livros API, siga estas etapas:

1. Bifurque este repositório.
2. Crie um branch: `git checkout -b <nome_branch>`.
3. Faça suas alterações e confirme-as: `git commit -m '<mensagem_commit>'`
4. Envie para o branch original: `git push origin <nome_repo>`.
5. Crie a solicitação de pull.

Como alternativa, consulte a documentação do GitHub em [como criar uma solicitação pull](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).
