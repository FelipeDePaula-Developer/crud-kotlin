#!/bin/bash

# Compilar o código
mvn clean package -DskipTests

# Executar a aplicação
java -jar target/thinkot_2-0.0.1-SNAPSHOT.jar