#!/bin/bash


if [[ -z "$KAFKA_TOPICS_TO_CREATE" ]]; then
	exit 0
fi

if [[ -z "$KAFKA_CONNECTION_TIMEOUT" ]]; then
	KAFKA_CONNECTION_TIMEOUT=500
fi

kafka_unreachable=false
seconds_exceeded=0
interval=10

while netstat -lnt | awk '$4 ~ /:'"$KAFKA_PORT"'$/ {exit}'; do
	echo "trying to connect to kafka..."
	sleep $interval;
	seconds_exceeded=$(( seconds_exceeded  + interval ))

	if [ $seconds_exceeded -gt $KAFKA_CONNECTION_TIMEOUT ]; then
		kafka_unreachable=true
		break
	fi
done

if $kafka_unreachable; then
	echo "Die Topics:\n $KAFKA_TOPICS_TO_CREATE\n anzulegen. Kafka ist unter dem Port: $KAFKA_PORT nicht zu erreichen."
	exit 1
fi

#format of Topic-String:
#nameOfTopic:partitions:replicas
for topic in $KAFKA_TOPICS_TO_CREATE; do
	echo "Erzeuge das Topic: $topic"
	IFS=':' read -r -a topicConfiguration <<< "$topic"

	$KAFKA_HOME/bin/kafka-topics.sh \
		--create \
		--zookeeper $KAFKA_ZOO_CONNECTION \
		--topic ${topicConfiguration[0]} \
		--partitions ${topicConfiguration[1]} \
		--replication-factor ${topicConfiguration[2]} 
done

wait
