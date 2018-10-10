FROM pschoe/alpine-java8

MAINTAINER pschoe

USER root

RUN wget https://archive.apache.org/dist/hadoop/core/hadoop-3.1.1/hadoop-3.1.1.tar.gz 
RUN tar zxfv ./hadoop-3.1.1.tar.gz
RUN mv ./hadoop-3.1.1 /usr/local/hadoop && rm ./hadoop-3.1.1.tar.gz

RUN mkdir /var/hadoop

ENV HADOOP_HOME /usr/local/hadoop
ENV PATH $PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
ENV HADOOP_CONF_DIR $HADOOP_HOME/etc/hadoop


RUN ssh-keygen -t rsa -f /root/.ssh/id_rsa -P '' && \
    cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys

COPY config/* /tmp/

RUN mv /tmp/ssh_config /root/.ssh/config \
 && mv /tmp/core-site.xml $HADOOP_CONF_DIR/core-site.xml \
 && mv /tmp/mapred-site.xml $HADOOP_CONF_DIR/mapred-site.xml \
 && mv /tmp/hdfs-site.xml $HADOOP_CONF_DIR/hdfs-site.xml \
 && mv /tmp/yarn-site.xml $HADOOP_CONF_DIR/yarn-site.xml \
 && mv /tmp/masters $HADOOP_CONF_DIR/masters \
 && mv /tmp/slaves $HADOOP_CONF_DIR/slaves

RUN /tmp/env.sh

RUN mkdir /root/data 
RUN mkdir /root/data/nameNode 
RUN mkdir /root/data/dataNode

RUN rm /tmp/*

CMD ["sh", "-c", "service ssh start; bash"]
