#!/bin/bash
#
# Install Kafka into a k8s cluster using Helm

# Add custom Helm repository
helm repo add bitnami https://charts.bitnami.com/bitnami

# Install durable 5-node Kafka cluster
readonly NODE_COUNT=5
readonly REPLICATION_FACTOR=3
readonly PARTITIONS=100
# Partitions = ConsumerReplicaCount * ParallelConsumerCount
# Partitions = DesiredThroughput / SingleConsumerThroughput
# ParallelConsumerCount = DesiredThroughput/ConsumerReplicaCount/SingleConsumerThroughput

# helm <v3
#helm install bitnami/kafka \
#  --name kafka-cluster \
#  --set replicaCount=$NODE_COUNT \
#  --set deleteTopicEnable="true" \
#  --set logRetentionHours=24 \
#  --set defaultReplicationFactor=$REPLICATION_FACTOR \
#  --set offsetsTopicReplicationFactor=3 \
#  --set transactionStateLogReplicationFactor=3 \
#  --set transactionStateLogMinIsr=3 \
#  --set numPartitions=$PARTITIONS

# helm >= v3
helm install kafka bitnami/kafka \
  --set replicaCount=$NODE_COUNT \
  --set deleteTopicEnable="true" \
  --set logRetentionHours=24 \
  --set defaultReplicationFactor=$REPLICATION_FACTOR \
  --set offsetsTopicReplicationFactor=3 \
  --set transactionStateLogReplicationFactor=3 \
  --set transactionStateLogMinIsr=3 \
  --set numPartitions=$PARTITIONS

# What happens if the producers send messages faster than the consumers can process them?
# What happens if nodes crash or temporarily go offline — are any messages lost?
