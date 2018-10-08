package org.pschoe.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ExampleProducer {
	private static String BROKERS = "localhost:9092,localhost:9093,localhost:9094";
	private static String TOPIC = "my-failsafe-topic";

	public static void main(String[] args) {
		System.out.println("Starting ExampleProducer...");

		runProducer();
	}


	private static void runProducer() {
		Producer<String, String> producer = createProducer();
		
		for(int i = 0; i < 100; i++) {
			producer.send(new ProducerRecord<String, String>(TOPIC, Integer.toString(i), Integer.toString(i)));
		}
		
		producer.close();
	}

	private static Producer<String, String> createProducer() {

		Properties props = new Properties();
		props.put("bootstrap.servers", BROKERS);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("linger.ms", 1);
		props.put("batch.size", 16384);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		return new KafkaProducer(props);
	}
}
