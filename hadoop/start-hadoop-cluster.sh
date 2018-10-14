#!/bin/bash


# Das beste wäre das Cluster über die Umgebungs Variablen eines Compose-Files zu erzeugen..



if [ -z "$1" ]
then
	nSlaves=3
else
	nSlaves=$1
fi

# if no second parameter for the net is specified it will be default 'hadoop-net'
hadoopnetName=${2:-"hadoop-net"}

# remove old cluster + network
# old container 
hadoopcontainer=$(docker container ls | awk '{ print $NF }' | grep 'hadoop-')
for container in ${hadoopcontainer[@]}; do
	echo "Entferne container ${container}."
	docker container rm ${container} -f
done

# create hadoopnet if not already present
dockernets=$(docker network ls | awk '{print $2}')
if [[ ! "${dockernets[@]}" =~ "$hadoopnetName" ]]; then
	docker network create -d bridge --subnet 172.25.0.0/16 $hadoopnetName
else
	echo "Musste das Docker-Net: $hadoopnetName nicht erzeugen da es schon vorhanden ist."
fi


#register workers in 'config/workers' 
rm config/workers
for (( j=1; j<=$nSlaves; j++ ))
do
	echo "hadoop-worker-$j" >> config/workers
done

# build image to ensure worker-file is correct
docker build -t pschoe/hadoop-base-image .

# creating master

echo "Erzeuge Master."
docker run -itd \
	--network=$hadoopnetName \
	--name hadoop-master \
	--hostname hadoop-master \
	-p 50070:50070 \
	-p 8088:8088 \
	-p 9870:9870 \
	-p 9864:9864 \
	-p 19888:19888 \
	-p 8888:8888 \
	pschoe/hadoop-base-image

# creating slaves
for (( i=1; i<=$nSlaves; i++ ))
do
	slavename="hadoop-worker-$i"
	echo "Erzeuge worker: $slavename."
	docker run -itd \
		--network=$hadoopnetName \
		--name $slavename \
		--hostname $slavename \
 		pschoe/hadoop-base-image
done
