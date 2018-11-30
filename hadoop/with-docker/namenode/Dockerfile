FROM pschoe/hadoop-base

HEALTHCHECK CMD curl -f http://localhost:9870/ || exit 1

RUN mkdir -p /hadoop/hdfs/namenode
VOLUME /hadoop/hdfs/namenode

ADD run.sh /run.sh
RUN chmod a+x /run.sh

EXPOSE 9870  

ENTRYPOINT ["/run.sh"]


