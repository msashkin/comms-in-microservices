#!/bin/bash
#
# Delete kafka deployed by helm

helm delete kafka
# Delete persistent volume claims
# Note: by default chart removal does not delete these persistent volume claims, so that old configuration files would be used.
kubectl get pvc # List persistent volume claims
kubectl delete pvc data-kafka-0
kubectl delete pvc data-kafka-1
kubectl delete pvc data-kafka-2
kubectl delete pvc data-kafka-3
kubectl delete pvc data-kafka-4
kubectl delete pvc data-kafka-zookeeper-0
