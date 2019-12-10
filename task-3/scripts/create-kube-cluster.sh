#!/bin/bash
#
# Create a cluster in Google Kubernetes Engine
# More cheap options, but might not work: f1-micro 0.2CPU/0.6Gb, g1-small 0.5CPU 1.7Gb

readonly CLUSTER_NAME=kafka-cluster

gcloud container clusters create "$CLUSTER_NAME" \
  --num-nodes=5 \
#  --machine-type="custom-1-1024" \
#  --disk-type="pd-standard" \
#  --disk-size="10GB"

# This will also update your kube config.
gcloud container clusters get-credentials $CLUSTER_NAME
