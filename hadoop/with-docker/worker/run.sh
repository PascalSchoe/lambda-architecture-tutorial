#!/bin/bash

/etc/init.d/ssh start

cp /hadoop/shared-resources/* $HADOOP_CONF_DIR/

hdfs --daemon start datanode
yarn --daemon start nodemanager

# keep container running
tail -f /dev/null
