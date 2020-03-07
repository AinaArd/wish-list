#!/usr/bin/env bash

echo "Stopping apps..."
docker-compose kill
docker-compose -f docker-compose.yml kill
docker rm $(docker ps -a -q)

sleep 3

docker container prune -f && docker network prune -f && docker volume prune -f

echo "\033[0;32m Cleanup is finished. \033[0m"
