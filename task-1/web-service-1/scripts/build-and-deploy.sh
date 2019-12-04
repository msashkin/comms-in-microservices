#!/bin/bash

cd ..

./mvnw clean package
docker build -t gcr.io/comms-in-microservices/task-1-web-service-1:latest .
docker push gcr.io/comms-in-microservices/task-1-web-service-1:latest

kubectl apply -f deployment.yml
kubectl apply -f service.yml
kubectl get service web-service-1
