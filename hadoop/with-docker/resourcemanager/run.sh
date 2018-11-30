#!/bin/bash


/etc/init.d/ssh start

cp /hadoop/shared-resources/* $HADOOP_CONF_DIR/


yarn --daemon start resourcemanager
mapred --daemon start historyserver

# keep container running
tail -f /dev/null
