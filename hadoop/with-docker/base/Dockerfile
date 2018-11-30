FROM pschoe/base-image-java8-ubuntu

ARG HADOOP_VERSION=3.1.1

ENV HADOOP_HOME /opt/hadoop
ENV PIG_HOME /opt/pig
ENV HADOOP_CONF_DIR /opt/hadoop/etc/hadoop
ENV PIG_CLASSPATH /opt/hadoop/etc/hadoop

# Hadoop
RUN wget http://apache.mirrors.tds.net/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz 
RUN tar -xzf hadoop-$HADOOP_VERSION.tar.gz && mv hadoop-$HADOOP_VERSION $HADOOP_HOME
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PIG_HOME/bin

# Hadoop Configuration
RUN \
   echo "export JAVA_HOME=$JAVA_HOME" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_DATANODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_NAMENODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_SECONDARYNAMENODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export YARN_RESOURCEMANAGER_USER=root" >> $HADOOP_CONF_DIR/yarn-env.sh \
&& echo "export YARN_NODEMANAGER_USER=root" >> $HADOOP_CONF_DIR/yarn-env.sh \
&& echo "PATH=$PATH:$HADOOP_HOME/bin" >> ~/.bashrc 


# SSH Configuration, needed for Pseudo-Distributed Hadoop
ADD config/ssh_config ~/.ssh/config
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
 && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
 && chmod 0600 ~/.ssh/authorized_keys


RUN wget http://www-us.apache.org/dist/pig/pig-0.17.0/pig-0.17.0.tar.gz\
 && tar -xzf pig-0.17.0.tar.gz\
 && mv pig-0.17.0 $PIG_HOME



RUN mkdir /hadoop
RUN mkdir $HADOOP_HOME/logs

