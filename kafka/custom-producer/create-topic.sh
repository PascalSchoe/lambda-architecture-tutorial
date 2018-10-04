#!/bin/bash

/usr/local/kafka/bin/kafka-topics.sh \
	--create \
	--replication-factor 3 \
	--topic my-example-topic \
	--zookeeper localhost:2181


/usr/local/kafka/bin/kafka-topics.sh --list \
	--zookeeper localhost:2181
