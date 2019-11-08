# task 1

Create a GCP project

```sh
gcloud projects create comms-in-microservices
gcloud config set project comms-in-microservices
gcloud config set compute/zone us-central1-b
gcloud auth configure-docker
```

Create a cluster in GKE

```sh
gcloud container clusters create comms-in-microservices-task-1 --num-nodes=3
gcloud container clusters get-credentials comms-in-microservices-task-1
```

Build an image (web-service)

```sh
cd web-service
./mvnw clean package
docker build -t gcr.io/comms-in-microservices/task-1-web-service:latest .
docker push gcr.io/comms-in-microservices/task-1-web-service:latest
```

Build an image (rpc-service)

```sh
cd rpc-service
./mvnw clean package
docker build -t gcr.io/comms-in-microservices/task-1-rpc-service:latest .
docker push gcr.io/comms-in-microservices/task-1-rpc-service:latest
```

Deploy the service into GKE (web-service)

```sh
cd web-service
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl get service web-service
```

Deploy the service into GKE (rpc-service)

```sh
cd rpc-service
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl get service rpc-service
```

Verify the service

```sh
curl http://{EXTERNAL-IP}:60000/api/hello
```

Update an image:

```sh
kubectl set image <object_type>/<object_name> <container_name>=<new_image_to_use>
```
