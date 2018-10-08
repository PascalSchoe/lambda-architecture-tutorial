# Cluster betreiben

Folgende Befehle sind in der angegebenen Reihenfolge auszuführen:
```Bash
$ ../run-zookeeper.sh
$ ./broker1/run-kafka-0.sh
$ ./broker2/run-kafka-1.sh
$ ./broker3/run-kafka-2.sh
$ ./create-topic.sh 
``` 

Anschließend kann die gewünschte Anzahl an *Consumer* und *Producer* gestartet werden, dafür werden die Scripte `start-consumer-console.sh` und `start-producer.sh` genutzt.

Nun kann ein in einem geöffnetem *Producer* eine Eingabe mit `Enter` bestätigt werden und sollte anschließend in der Konsole **aller** Consumer auftauchen, dies ist der fall weil sich alle *Consumer* noch in unterschiedlichen Gruppen befinden werden die Consumer in der gleichen Gruppe gestartet so wird die 'Last' auf alle Consumer einer Gruppe aufgeteilt.
