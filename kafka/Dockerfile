FROM pschoe/alpine-java8

MAINTAINER pschoe

#Download Kafka
RUN wget http://www-eu.apache.org/dist/kafka/1.1.0/kafka_2.12-1.1.0.tgz \
 && tar xzvf kafka_2.12-1.1.0.tgz \
 && mv kafka_2.12-1.1.0 /usr/local/kafka \
 && rm kafka_2.12-1.1.0.tgz 


ENV KAFKA_HOME=/usr/local/kafka
RUN mkdir $KAFKA_HOME/custom_scripts 
ENV Path=${Path}:${KAFKA_HOME}/bin
ENV KAFKA_ZOO_CONNECTION=localhost:2181

#Copy shell scripts onto container
COPY create-topics.sh start-kafka.sh run-kafka.sh run-zookeeper.sh lab1/ /tmp/

RUN chmod a+x /tmp/*.sh

#move file to destination
RUN mv /tmp/run-kafka.sh $KAFKA_HOME/custom_scripts \
 && mv /tmp/start-kafka.sh $KAFKA_HOME/custom_scripts \
 && mv /tmp/run-zookeeper.sh $KAFKA_HOME/custom_scripts \
 && mv /tmp/create-topics.sh $KAFKA_HOME/custom_scripts \
 && mv /tmp/list-topics.sh $KAFKA_HOME/custom_scripts \
 && mv /tmp/start-consumer-console.sh $KAFKA_HOME/custom_scripts \
 && mv /tmp/start-producer-console.sh $KAFKA_HOME/custom_scripts \
 && rm -rf /tmp

CMD ["sh", "-c", "${KAFKA_HOME}/custom_scripts/start-kafka.sh; bash"]


