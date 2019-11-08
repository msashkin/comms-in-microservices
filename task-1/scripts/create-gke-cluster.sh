#!/bin/bash

gcloud container clusters create comms-in-microservices-task-1 --num-nodes=3
gcloud container clusters get-credentials comms-in-microservices-task-1
