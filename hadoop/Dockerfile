FROM pschoe/base-image-jave8-ubuntu

ARG HADOOP_VERSION=3.1.1

ENV HADOOP_HOME=/opt/hadoop
ENV PIG_HOME=/opt/pig
ENV HADOOP_CONF_DIR=/opt/hadoop/etc/hadoop
ENV PIG_CLASSPATH=$HADOOP_CONF_DIR

# Hadoop
RUN wget http://apache.mirrors.tds.net/hadoop/common/hadoop-$HADOOP_VERSION/hadoop-$HADOOP_VERSION.tar.gz 
RUN tar -xzf hadoop-$HADOOP_VERSION.tar.gz && mv hadoop-$HADOOP_VERSION $HADOOP_HOME
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

# Hadoop Configuration
RUN \
   echo "export JAVA_HOME=$JAVA_HOME" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_DATANODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_NAMENODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export HDFS_SECONDARYNAMENODE_USER=root" >> $HADOOP_CONF_DIR/hadoop-env.sh \
&& echo "export YARN_RESOURCEMANAGER_USER=root" >> $HADOOP_CONF_DIR/yarn-env.sh \
&& echo "export YARN_NODEMANAGER_USER=root" >> $HADOOP_CONF_DIR/yarn-env.sh \
&& echo "PATH=$PATH:$HADOOP_HOME/bin" >> ~/.bashrc 

ADD config/*.xml $HADOOP_CONF_DIR/ 

# SSH Configuration, needed for Pseudo-Distributed Hadoop
ADD config/ssh_config ~/.ssh/config
ADD config/workers $HADOOP_CONF_DIR/workers
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
 && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
 && chmod 0600 ~/.ssh/authorized_keys

EXPOSE 8088 9870 9864 9866 8042 8888

ADD run.sh run.sh
RUN chmod a+x run.sh


RUN wget http://www-us.apache.org/dist/pig/pig-0.16.0/pig-0.16.0.tar.gz\
 && tar -xzf pig-0.16.0.tar.gz\
 && mv pig-0.16.0 $PIG_HOME


ENV PATH=$PATH:$PIG_HOME/bin






CMD bash run.sh
