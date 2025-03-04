# Usando uma imagem base do OpenJDK 17
FROM openjdk:20-jdk-slim

# Definindo o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiando o arquivo JAR gerado da aplicação para o contêiner
COPY target/sub_tech_challenge_seller_api_fase5-0.0.1.jar app.jar

# Expondo a porta que a aplicação irá rodar
EXPOSE 8081

# Definindo o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
