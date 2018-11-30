FROM pschoe/hadoop-base

HEALTHCHECK CMD curl -f http://localhost:8032/ || exit 1

ADD run.sh /run.sh
RUN chmod a+x /run.sh

EXPOSE 8088 19888 8188

ENTRYPOINT ["/run.sh"]

