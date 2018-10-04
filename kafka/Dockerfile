FROM pschoe/alpine-java8

MAINTAINER pschoe

#Download Kafka
RUN wget http://www-eu.apache.org/dist/kafka/1.1.0/kafka_2.12-1.1.0.tgz \
 && tar xzvf kafka_2.12-1.1.0.tgz \
 && mv kafka_2.12-1.1.0 /usr/local/kafka \
 && rm kafka_2.12-1.1.0.tgz 

ENV KAFKA_HOME=/usr/local/kafka
ENV Path=${Path}:${KAFKA_HOME}/bin

#Copy shell scripts onto container
COPY run-kafka.sh run-zookeeper.sh lab1/ /tmp/

#move file to destination
RUN mv /tmp/run-kafka.sh $KAFKA_HOME/ \
&& mv /tmp/run-zookeeper.sh $KAFKA_HOME/ \
&& mkdir $KAFKA_HOME/lab1 \
&& mv /tmp/create-topic.sh $KAFKA_HOME/lab1 \
&& mv /tmp/list-topics.sh $KAFKA_HOME/lab1 \
&& mv /tmp/start-consumer-console.sh $KAFKA_HOME/lab1 \
&& mv /tmp/start-producer-console.sh $KAFKA_HOME/lab1 

#start kafka
RUN $KAFKA_HOME/run-zookeeper.sh &\
&& $KAFKA_HOME/run-kafka.sh &

#only for debugging...
#create dummy-topic
RUN $KAFKA_HOME/lab1/create-topic.sh 

#start consoles
RUN $KAFKA_HOME/lab1/start-consumer-console.sh \
&& $KAFKA_HOME/lab1/start-producer-console.sh

CMD ["sh", "-c", "bash"]
