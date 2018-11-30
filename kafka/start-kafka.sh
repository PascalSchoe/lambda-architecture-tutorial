#!/bin/bash

if [[ -z "$KAFKA_ZOO_CONNECTION" ]]; then
	echo "ERROR: Kafka benoetigt eine Verbingung zu Zookeeper..."
	echo "Verwende hierfuer die 'KAFKA_ZOO_CONNECTION'-Environment-Variable\n"
	exit 1
fi

#default kafka port if not set before, for instance in compose.yml as env var
if [[ -z "$KAFKA_PORT" ]]; then
	export KAFKA_PORT=9092
fi

#creating topics, if there are any defined in the env '$KAFKA_TOPICS_TO_CREATE'
#$KAFKA_HOME/custom_scripts/create-topics.sh &
#unset KAFKA_TOPICS_TO_CREATE


#$KAFKA_HOME/custom_scripts/run-zookeeper.sh &

exec "$KAFKA_HOME/bin/kafka-server-start.sh" "$KAFKA_HOME/config/server.properties"


