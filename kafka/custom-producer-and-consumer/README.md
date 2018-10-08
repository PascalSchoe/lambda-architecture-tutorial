# Cluster betreiben

Folgende Befehle sind in der angegebenen Reihenfolge auszuführen:
```Bash
$ ../run-zookeeper.sh
$ ./broker1/run-kafka-0.sh
$ ./broker2/run-kafka-1.sh
$ ./broker3/run-kafka-2.sh
$ ./create-topic.sh 
``` 

Unter `./learning-kafka` findest du ein Projekt in diesem werden eigene Consumer und Producer für das *my-failsafe-topic* -Thema erstellt, um diese zu testen rufe einfach nur die entsprechenden `Main`-Methoden auf.
