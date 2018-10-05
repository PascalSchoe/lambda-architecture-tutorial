#Cluster betreiben

Folgende Befehle sind in der angegebenen Reihenfolge auszuführen:
```Bash
$ ../run-zookeeper.sh
$ ./broker1/run-kafka-0.sh
$ ./broker2/run-kafka-1.sh
$ ./broker3/run-kafka-2.sh
$ ./create-topic.sh 
``` 

Anschließend kann die gewünschte Anzahl an *Consumer* und *Producer* gestartet werden, dafür werden die Scripte `start-consumer-console.sh` und `start-producer.sh` genutzt.
