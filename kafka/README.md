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
Die Logs werden wiederum in Partitions und Segments unterteilt. Topic log Partitions sind eine Möglichkeit Kafkas um schnelleres Schreiben von Daten zu ermöglichen. Ein weiterer Vorteil von Partitions ist das mehrere Kafka-Consumer gleichzeitig auf den Daten arbeiten können. 
Kafka replikiert die Partitions auf mehrere nodes um eine hohe Verfügbarkeit zu erzielen.

### Was ist ein ISR?
Akronym für *In-Sync-Replica*. Hierbei handelt es sich um einen Broker der für den Fall eines Ausfalls des Leaders die entsprechende Partition synchron vorhält.
So ist der Broker im Fall der Fälle gerüstet nahtlos zu übernehmen.

## Producer
Kafka API die genutzt wird um Streams von Records zu erzeugen.

Kafka producer schreiben zu Topics.

## Consumer
Kafka API, wird genutzt um Streams von Records von Kafka zu konsumieren.


## Broker
Ein Kafka-Server dieser befindet sich im Kafka-Cluster, anders gesagt ein **mehrere** Broker stellen ein Cluster dar.

### Leader 
Ein *Leader* ist der Broker der für eine gewisse Partition eines Topics alle Lese- und Schreibzugriffe koordiniert.
*Follower* hingegen replizieren lediglich den Leader um gegebenenfalls zu übernehmen, siehe ISR.

## Welche Rolle spiel Zookeeper?
Zookeeper wird genutzt um das Cluster aus Brokern zu koordinieren. Zusätzlich kann hier die Konfiguration von zentraler Stelle ausgeschehen.
Zookeeper kümmert sich ebenfalls darum den *Leader* unter den Brokern festzulegen, der Leader gilt anschließend für die jeweilige Partition.

## Empfohlene Architektur

- mindestens **drei Kafka-Broker**
- mindestens eine **Partition-Replication von zwei bis drei**