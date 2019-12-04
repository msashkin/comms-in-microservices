#!/bin/bash

gcloud container clusters create comms-in-microservices-task-1 --num-nodes=3 "n1-standard-1" --disk-type="pd-standard" --disk-size="10GB"
gcloud container clusters get-credentials comms-in-microservices-task-1
