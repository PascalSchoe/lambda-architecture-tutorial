#!/bin/bash

if [ -z "$1" ]
then
	nSlaves=1
else
 	nSlaves=$1	
fi

echo "nSlaves --> [$nSlaves]"

#removing old entries
cat /dev/null > ./config/slaves

for (( i=1; i<=$nSlaves; i++ ))
do
	echo "Registriere slave$i im File './config/slaves'"
	echo "slave$i" >> ./config/slaves
done

echo "Erzeuge Netzwerk fuer Hadoop"
docker network rm hadoopnet && docker network create -d bridge --subnet 172.25.0.0/16 hadoopnet

#creating image from Dockerfile in current directory
docker build -t pschoe/hadoop .

#creating container from image
echo "starting container for master"
docker run -itd --network="hadoopnet" --ip 172.25.0.100 -p 50070:50070 -p 8088:8088 --name master --hostname master pschoe/hadoop

for (( j=1; j<=$nSlaves; j++ ))
do
	slavename="slave$j"
	echo "starting container for $slavename"
	docker run -itd --network="hadoopnet" --ip 172.25.0.10$j --name $slavename --hostname $slavename pschoe/hadoop
done

docker exec -ti master bash -c "hdfs namenode -format && /usr/local/hadoop/sbin/start-dfs.sh && /usr/local/hadoop/sbin/start-yarn.sh"
docker exec -ti master bash
