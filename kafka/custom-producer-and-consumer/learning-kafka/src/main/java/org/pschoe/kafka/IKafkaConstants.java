package org.pschoe.kafka;

public interface IKafkaConstants {

	public static String KAFKA_BROKERS = "localhost:9092,localhost:9093,localhost:9094";
	public static Integer MESSAGE_COUNT = 1000;
	public static String CLIENT_ID = "client1";
	public static String TOPIC_NAME = "my-failsafe-topic";
	public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

}
