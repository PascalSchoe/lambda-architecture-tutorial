# Wie starte ich das System?

--> ```$ ./start-hadoop-cluster.sh <num_workers>```

Sollte die Anzahl der *hadoop-worker* nicht angegeben werden so werden automatisch drei worker erzeugt.

# TODO
1. Dockerimage mit Argumenten starten:
        - zb. *num_workers* so wäre *.start-hadoop-cluster* nach einigen Anpassungen nicht mehr von Nöten.
2. start-hadoop-cluster --> nodes auf Zielmaschine unter '/etc/hosts' hinterlegen
3. yarn-resource configuration
