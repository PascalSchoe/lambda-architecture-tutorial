FROM pschoe/base-image-java8-ubuntu

MAINTAINER pschoe

ENV KAFKA_HOME=/opt/kafka
ENV Path=${Path}:${KAFKA_HOME}/bin
ENV KAFKA_ZOO_CONNECTION=localhost:2181


#Download Kafka
RUN wget http://www-eu.apache.org/dist/kafka/1.1.0/kafka_2.12-1.1.0.tgz \
 && tar xzvf kafka_2.12-1.1.0.tgz \
 && mv kafka_2.12-1.1.0 $KAFKA_HOME \
 && rm kafka_2.12-1.1.0.tgz 


#Copy shell scripts onto container
COPY create-topics.sh start-kafka.sh run-kafka.sh lab1/ /tmp/

RUN chmod a+x /tmp/*.sh

#move file to destination
RUN mv /tmp/run-kafka.sh /usr/bin \
 && mv /tmp/start-kafka.sh /usr/bin \
 && mv /tmp/create-topics.sh /usr/bin \
 && mv /tmp/list-topics.sh /usr/bin \
 && mv /tmp/start-consumer-console.sh /usr/bin \
 && mv /tmp/start-producer-console.sh /usr/bin \
 && rm -rf /tmp

VOLUME ["/kafka"]

CMD ["sh", "-c", "start-kafka.sh; bash"]


