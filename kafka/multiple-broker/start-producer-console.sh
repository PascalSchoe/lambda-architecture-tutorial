#!/bin/bash


cd /usr/local/kafka

./bin/kafka-console-producer.sh \
	--broker-list localhost:9092 localhost:9093 localhost:9094 \
	--topic my-failsafe-topic
