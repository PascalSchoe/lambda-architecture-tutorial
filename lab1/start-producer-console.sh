#!/bin/bash


cd /usr/local/kafka

./bin/kafka-console-producer.sh \
	--broker-list localhost:9092 \
	--topic my-topic
