#!/bin/bash
#
# Delete a cluster in Google Kubernetes Engine

readonly CLUSTER_NAME=kafka-cluster

gcloud container clusters delete "$CLUSTER_NAME"
