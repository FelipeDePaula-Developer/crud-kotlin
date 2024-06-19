# Usar a imagem oficial do Maven com OpenJDK 17 como base
FROM maven:3.8.5-openjdk-17-slim AS build

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo pom.xml e outros arquivos necessários para resolver dependências
COPY pom.xml .

# Baixar as dependências
RUN mvn dependency:go-offline

# Copiar o código-fonte do aplicativo
COPY src ./src

# Executar o comando Maven para compilar e empacotar a aplicação
RUN mvn clean package -DskipTests

# Listar arquivos no diretório target para depuração
RUN ls -l /app/target

# Usar uma imagem menor do OpenJDK 17 para a execução
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

COPY --from=build /app/target/thinkot_2-0.0.1-SNAPSHOT.jar thinkot_2.jar
# Instalar Maven no ambiente de execução
RUN apt-get update && apt-get install -y maven

# Adicionar um script de inicialização que compila o código e executa a aplicação
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

CMD ["/app/entrypoint.sh"]