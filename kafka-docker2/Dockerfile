FROM openjdk:8u171-jre-alpine

ARG kafka_version=2.0.0
ARG scala_version=2.12
ARG glibc_version=2.27-r0


MAINTAINER pschoe

ENV 	KAFKA_VERSION=$kafka_version \
	SCALA_VERSION=$scala_verison \
	KAFKA_HOME=/opt/kafka \		
	GLIBC_VERSION=$glibc_version	

ENV 	PATH=${PATH}:${KAFKA_HOME}/bin

COPY download-kafka.sh:q

