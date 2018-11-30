FROM pschoe/base-image-java8-ubuntu

ENV ZK_HOME=/opt/zookeeper

# Zookeeper
RUN wget http://mirror.softaculous.com/apache/zookeeper/zookeeper-3.4.13/zookeeper-3.4.13.tar.gz \
 && tar -xzvf zookeeper-3.4.13.tar.gz \
 && mv zookeeper-3.4.13 $ZK_HOME \
 && rm zookeeper-3.4.13.tar.gz 


# preinstallated configuration files
COPY config/* /tmp/

RUN mkdir $ZK_HOME/data \
 && mv /tmp/zoo.cfg $ZK_HOME/conf

VOLUME ["$ZK_HOME/data"]

EXPOSE 2181 2888 3888

CMD ["sh", "-c", "service ssh start;$ZK_HOME/bin/zkServer.sh start; bash"]


