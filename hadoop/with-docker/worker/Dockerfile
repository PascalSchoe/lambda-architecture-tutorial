FROM pschoe/hadoop-base

HEALTHCHECK CMD curl -f http://localhost:9864/ || exit 1

RUN mkdir -p /hadoop/hdfs/datanode
VOLUME /hadoop/hdfs/datanode

ADD run.sh /run.sh
RUN chmod a+x /run.sh

EXPOSE 9866 8042

ENTRYPOINT ["/run.sh"]


