#!/bin/bash

cd ..

./mvnw clean package
docker build -t gcr.io/comms-in-microservices/task-1-rpc-service-2:latest .
docker push gcr.io/comms-in-microservices/task-1-rpc-service-2:latest

kubectl apply -f deployment.yml
kubectl apply -f service.yml
kubectl get service rpc-service-2
