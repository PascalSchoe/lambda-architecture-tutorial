#!/bin/bash

if [ -z "$1" ]
then
	nSlaves=1
else
	nSlaves=$1
fi

# if no second parameter for the net is specified it will be default 'hadoop-net'
hadoopnetName=${2:-"hadoop-net"}

# remove old cluster + network
# old container 
hadoopcontainer=$(docker container ls | awk 'print{ $NF }' | grep 'hadoop-')
docker container rm $hadoopcontainer -f

# create hadoopnet if not already present
dockernets=$(docker network ls | awk '{print $4}')
if [[ ! "${dockernets[@]}" =~ "$hadoopnetName" ]]; then
	docker network create -d bridge --subnet 172.25.0.0/16 $hadoopnetName
else
	echo "Musste das Docker-Net: $hadoopnetName nicht erzeugen da es schon vorhanden ist."
fi

# creating slaves
for (( i=1; i<=$nSlaves; i++ ))
do
	slavename="slave$j"
	echo "erzeuge worker: $slavename"
done
