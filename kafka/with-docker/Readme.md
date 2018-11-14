# So startest du das Kafka-Cluste
```bash
$ docker-compose up
```

Docker(-Compose) startet nun für dich die, im **docker-compose.yml** beschriebenen, services. Anschließend stehen die folgenden Dienste unter Verwendung des entsprechend Ports zur Verfügung.

|Service|Port|
|---|---|
|Broker1|9092|
|Broker2|9093|
|Broker3|9094|
|Zookeeper|2181|
|Schema-Registry|8081|
|Schema-Registry-UI|8001|
|Kafka-Rest-Proxy|8082|
|Kafka-Topics-UI|8002|
|Zookeeper|2181|

Im nächsten Schritt erzeugst du nun ein **Avro-Schema** über die **Schema-Registry-UI**.
```json
{
  "type" : "record",
  "name" : "Blender",
  "namespace" : "org.kitchenstuff.tools",
  "doc" : "The question is: 'Will it blend?'",
  "fields" : [ {
    "name" : "model",
    "type" : "string"
  }, {
    "name" : "make",
    "type" : "string"
  }, {
    "name" : "rotations",
    "type" : "int"
  } ]
}
```

Nachdem du das Schema erstellt hast benötigen wir noch ein **Kafka-Topic** über dieses kann unser System kommunizieren.
```bash
$ docker exec -ti with-docker_kafka1_1 bash
$ kafka-topics --create --zookeeper zoo1:2181 --replication-factor 1 --partitions 1 --topic new-blender
```

Nun ist es dir möglich die Consumer und Producer im Projekt zu starten.
