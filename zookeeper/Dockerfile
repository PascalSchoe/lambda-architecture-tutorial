FROM ubuntu:14.04

WORKDIR /root

# Java
RUN apt-get update && \
    apt-get install -y openssh-server openjdk-7-jdk wget && \
    rm -rf /var/lib/apt/lists/* && \	
    apt-get clean


ENV JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# Zookeeper
RUN wget http://mirror.softaculous.com/apache/zookeeper/zookeeper-3.4.13/zookeeper-3.4.13.tar.gz && \
    tar -xzvf zookeeper-3.4.13.tar.gz && \
    mv zookeeper-3.4.13 /usr/local/zookeeper && \
    rm zookeeper-3.4.13.tar.gz && \
    mkdir data	

ENV ZOOKEEPER_HOME=/usr/local/zookeeper

# preinstallated configuration files
COPY config/* /tmp/

RUN mkdir $ZOOKEEPER_HOME/data && \
    mv /tmp/zoo.cfg $ZOOKEEPER_HOME/conf

VOLUME ["$ZOOKEEPER_HOME/data"]

EXPOSE 2181

CMD ["sh", "-c", "service ssh start;$ZOOKEEPER_HOME/bin/zkServer.sh start; bash"]


