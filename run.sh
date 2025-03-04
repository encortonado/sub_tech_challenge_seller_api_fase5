#!/bin/sh

option=$1

case $option in
  db) 
    docker-compose up
    ;;
  app)
    mvn clean install -DskipTests -Dspring.profiles.active=local
    docker build --rm -t temp-api-app . && docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local temp-api-app
    ;;
  clear)
    docker-compose down
    ;;
  *) echo 'Comando não encontrado!' ;;
esac
