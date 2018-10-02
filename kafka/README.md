# Kafka Architektur
Folgende Komponenten arbeiten unter Kafka zusammen:
- **Record**
- **Topic**
- **Consumer**
- **Producer**
- **Broker**
- **Partition**
- **Cluster**

Nachfolgend werden die einzelnen Komponenten erläutert.

## Record
*--> beinhaltet Key[optional], value und timestamp*
Kafka-Records sind immutable.

## Topic
Ein Topic ist ein *stream* von Records, zb. "/user-messages" oder "/temperatures". Jedes Topic hat ein *Log* dieses dient als Speicher auf der Festplatte.
Die Logs werden wiederum in Partitions und Segments unterteilt.

## Producer
Kafka API die genutzt wird um Streams von Records zu erzeugen.

## Consumer
Kafka API, wird genutzt um Streams von Records von Kafka zu konsumieren.

## Broker
Ein Kafka-Server dieser befindet sich im Kafka-Cluster, anders gesagt ein **mehrere** Broker stellen ein Cluster dar.

