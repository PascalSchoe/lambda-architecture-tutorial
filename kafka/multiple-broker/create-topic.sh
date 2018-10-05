#!/bin/bash


cd /usr/local/kafka/

bin/kafka-topics.sh \
	--create \
	--zookeeper localhost:2181 \
	--replication-factor 3 \
	--partitions 13 \
	--topic my-failsafe-topic
