# Fase de construção
FROM ubuntu:latest AS build

# Atualizar e instalar dependências necessárias
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de código para o contêiner
COPY . .

# Construir o projeto Maven
RUN mvn clean install

# Fase final
FROM openjdk:21-jdk-slim

# Definir o diretório de trabalho na imagem final
WORKDIR /app

# Copiar o jar construído na fase de build para a fase final
COPY --from=build /app/target/*.jar /app/app.jar

# Expor a porta que a aplicação vai utilizar
EXPOSE 8080

# Definir o comando de entrada padrão para a imagem final
ENTRYPOINT ["java", "-jar", "app.jar"]
