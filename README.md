# Projeto Base

Esse repositório contém código e configurações base para as atividades/projetos da disciplina "Desenvolvimento de Aplicações Corporativas" do IFPB - Campus Cajazeiras. Os requisitos necessários para a execução desse projeto são:

- JDK versão 17. Download [aqui](https://www.oracle.com/br/java/technologies/downloads/#jdk17) ou use um gerenciador de versões como [SDKMAN!](https://sdkman.io/install) (Recomendado!)
- [Docker](https://www.docker.com/products/docker-desktop/)

# Executando

Ao fazer o clone do repositório, abra o terminal no diretório do projeto e execute os comandos linha por linha.

```bash
./mvnw clean
./mvnw install
./mvnw spring-boot:run
```

# Evoluindo o projeto

- Para adicionar novas versões do banco de dados, adicione um novo arquivo de migração no diretório `src/main/resources/db/migration` seguindo o padrão `Vx__nome.sql`, onde `x` é a versão da migração e `nome` é um nome descritivo da migração. Exemplo: `V2__criar_tabela_usuario.sql`. Ao executar o projeto, o Flyway vai migrar o banco de dados para a última versão disponível.

# Resolução de problemas
- Pode ser que o comando do docker seja `docker-compose` (com hífen) na sua máquina. Faça o ajuste de acordo;
- Pode ser que você tenha o PostgreSQL instalado na sua máquina. Se for esse o caso, altere o arquivo `docker-compose.yml`, substituindo o mapeamento da porta na linha 11 por outra porta além da `5432`. Exemplo utilizando a porta 5433: `5433:5432`. No caso de alterar essa porta, altere a URL de conexão no arquivo `application.properties`;
- Em caso do erro "No compiler is provided in this environment" ao executar os comandos `mvnw`, verifique se a variável JAVA_HOME está definida no seu ambiente. Caso não esteja, siga [esse tutorial](https://www.baeldung.com/java-home-on-windows-mac-os-x-linux)
