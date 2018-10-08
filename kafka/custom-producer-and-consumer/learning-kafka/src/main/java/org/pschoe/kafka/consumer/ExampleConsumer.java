package org.pschoe.kafka.consumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ExampleConsumer {
	private static String BROKERS = "localhost:9092,localhost:9093,localhost:9094";
	private static List TOPICS = Arrays.asList(
			"my-failsafe-topic"
	);
	
	
	
	
	public static void main(String[] args) {
		subscribe();
	}
	
	private static void subscribe() {
		Consumer consumer = createConsumer();
		consumer.subscribe(TOPICS);
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for(ConsumerRecord cr : records) {
				System.out.printf("offset = %d, key = %s, value %s%n", cr.offset(), cr.key(), cr.value());
			}
		}
	}

	private static Consumer<String, String> createConsumer() {
		Properties props = new Properties();
		
		props.put("bootstrap.servers", BROKERS);
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		
		return consumer;
	}
}
