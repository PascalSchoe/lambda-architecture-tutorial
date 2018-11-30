#!/bin/bash

/etc/init.d/ssh start

cp /hadoop/shared-resources/* $HADOOP_CONF_DIR/

# keep container running
tail -f /dev/null
