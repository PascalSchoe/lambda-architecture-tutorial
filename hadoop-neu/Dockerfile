FROM ubuntu:16.04

ARG HADOOP_VERSION=3.1.1

ENV HADOOP_HOME=/opt/hadoop
ENV HADOOP_CONF_DIR=/opt/hadoop/etc/hadoop

ENV JAVA_HOME=/usr/lib/jvm/java-8-oracle

RUN touch /etc/apt/apt.conf.d/99fixbadproxy \
&& echo "Acquire::http::Pipeline-Depth 0;" >> /etc/apt/apt.conf.d/99fixbadproxy \
&& echo "Acquire::http::No-Cache true;" >> /etc/apt/apt.conf.d/99fixbadproxy \
&& echo "Acquire::BrokenProxy true;" >> /etc/apt/apt.conf.d/99fixbadproxy \
&& apt-get update -o Acquire::CompressionTypes::Order::=gz \
&& apt-get clean \
&& rm -rf /var/lib/apt/lists/* \
&& apt-get update -y

RUN apt-get install -y  software-properties-common 
RUN apt-get install -y  debconf-utils
RUN apt-get install -y  ssh
RUN apt-get install -y  vim 
RUN apt-get install -y  rsync 

# Java8 
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections
RUN apt-get install -y oracle-java8-installer
ENV PATH=$PATH:$JAVA_HOME/bin

# Hadoop
RUN wget http://apache.mirrors.tds.net/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz 
RUN tar -xzf hadoop-$HADOOP_VERSION.tar.gz && mv hadoop-$HADOOP_VERSION $HADOOP_HOME
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

# Hadoop Configuration
RUN \
   echo "export JAVA_HOME=$JAVA_HOME" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_DATANODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_NAMENOE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_SECONDARYNAMENODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export YARN_RESOURCEMANAGER_USER=root" >> $HADOOP_CONF_DIR/yarn-env.sh \
&& echo "export YARN_NODEMANAGER_USER=root" >> $HADOOP_CONF_DIR/yarn-env.sh \
&& echo "PATH=$PATH:$HADOOP_HOME/bin" >> ~/.bashrc 

ADD config/*.xml $HADOOP_CONF_DIR/ 

# SSH Configuration, needed for Pseudo-Distributed Hadoop
ADD config/ssh_config ~/.ssh/config
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
 && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
 && chmod 0600 ~/.ssh/authorized_keys

EXPOSE 8088 9870 9864 19888 8042 8888

ADD run.sh run.sh
RUN chmod a+x run.sh

CMD bash run.sh
