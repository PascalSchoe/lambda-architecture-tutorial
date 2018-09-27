FROM pschoe/zookeeper:1.1

WORKDIR /root

RUN wget http://www-eu.apache.org/dist/kafka/1.1.0/kafka_2.12-1.1.0.tgz \
 && tar -xzvf kafka_2.12-1.1.0.tgz \
 && mv kafka_2.12-1.1.0 /usr/local/kafka \
 && rm kafka_2.12-1.1.0.tgz 

CMD ["sh", "-c", "service start ssh; bash"]
