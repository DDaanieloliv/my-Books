# Usar uma imagem base do OpenJDK
FROM openjdk:17-jdk-alpine

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo JAR gerado pelo Maven
COPY target/My-Books-0.0.1-SNAPSHOT.jar /app/My-Books-0.0.1-SNAPSHOT.jar

# Comando para rodar a aplicação
CMD ["java", "-jar", "My-Books-0.0.1-SNAPSHOT.jar"]