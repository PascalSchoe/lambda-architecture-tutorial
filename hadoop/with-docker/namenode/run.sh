#!/bin/bash

/etc/init.d/ssh start

cp /hadoop/shared-resources/* $HADOOP_CONF_DIR/

if [ -z "$(ls -A /hadoop/hdfs/namenode)" ]; then
	hdfs namenode -format
fi

hdfs --daemon start namenode

# keep container running
tail -f /dev/null
