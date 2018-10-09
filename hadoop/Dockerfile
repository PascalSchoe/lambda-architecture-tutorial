FROM pschoe/alpine-java8

MAINTAINER pschoe

RUN wget https://archive.apache.org/dist/hadoop/core/hadoop-3.1.1/hadoop-3.1.1.tar.gz 
RUN tar zxfv ./hadoop-3.1.1.tar.gz
RUN mv ./hadoop-3.1.1 /usr/local/hadoop && rm ./hadoop-3.1.1.tar.gz

RUN mkdir /var/hadoop

ENV HADOOP_HOME /usr/local/hadoop
ENV PATH $PATH:$HADOOP_HOME/bin
ENV HADOOP_CONF_DIR $HADOOP_HOME/etc/hadoop

RUN ssh-keygen -t rsa -f ~/.ssh/id_rsa -P '' && \
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

COPY config/* /tmp/

RUN mv /tmp/ssh_config ~/.ssh/config \
 && mv /tmp/core-site.xml $HADOOP_CONF_DIR/core-site.xml \
 && mv /tmp/mapred-site.xml $HADOOP_CONF_DIR/mapred-site.xml \
 && mv /tmp/hdfs-site.xml $HADOOP_CONF_DIR/hdfs-site.xml \
 && mv /tmp/masters $HADOOP_CONF_DIR/masters \
 && mv /tmp/slaves $HADOOP_CONF_DIR/slaves

RUN rm /tmp/*

CMD ["sh", "-c", "service ssh start; bash"]
