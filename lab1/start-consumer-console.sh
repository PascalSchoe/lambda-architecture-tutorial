#!/bin/bash


cd /usr/local/kafka

./bin/kafka-console-consumer.sh \
	--bootstrap-server localhost:9092 \
	--zookeeper localhost:2181 \
	--topic my-topic \
	--from-beginning
